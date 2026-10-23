package tests.api;

import config.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/** Read-only WS9 pilot. Endpoint and selected user are configured per run. */
public class UserLookupPilotTest {
    private RequestSpecification request;
    private int userId;
    private Path evidence;

    @BeforeClass
    public void setUp() throws Exception {
        String baseUrl = ConfigReader.apiBaseUrl();
        Assert.assertNotNull(baseUrl, "Configure api.base.url for the selected test service");
        String selectedId = ConfigReader.get("api.pilot.user.id");
        Assert.assertNotNull(selectedId, "Select an existing user ID for this pilot");
        userId = Integer.parseInt(selectedId);
        Assert.assertTrue(userId > 0, "Selected user ID must be positive");
        int timeout = ConfigReader.getInt("api.timeout.ms", 15000);
        Assert.assertTrue(timeout >= 1000 && timeout <= 60000, "Use a bounded API timeout");
        request = new RequestSpecBuilder().setBaseUri(baseUrl).setAccept("application/json")
                .setConfig(RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", timeout).setParam("http.socket.timeout", timeout)))
                .build();
        String folder = System.getProperty("ws9.evidence.dir");
        Assert.assertNotNull(folder, "Select an evidence directory for this run");
        evidence = Path.of(folder);
        Files.createDirectories(evidence);
    }

    private Response get(String caseId, String name, String path, Integer filterId) throws Exception {
        RequestSpecification call = given().spec(request);
        if (filterId != null) call.queryParam("id", filterId);
        Response response = call.when().get(path);
        Map<String, Object> capture = new HashMap<>();
        capture.put("case_id", caseId);
        capture.put("method", "GET");
        capture.put("path", path);
        capture.put("filter_id", filterId);
        capture.put("status", response.statusCode());
        capture.put("content_type", response.contentType());
        capture.put("observed_at", Instant.now().toString());
        capture.put("duration_ms", response.time());
        capture.put("body", response.asString());
        new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValue(evidence.resolve(caseId + "-" + name + ".json").toFile(), capture);
        Assert.assertEquals(response.statusCode(), 200, caseId + ": status");
        Assert.assertTrue(response.contentType().toLowerCase().contains("application/json"), caseId + ": JSON response");
        return response;
    }

    private void assertIdentity(Map<String, Object> user, int expectedId) {
        Assert.assertTrue(user.get("id") instanceof Number, "User ID must be numeric");
        Assert.assertEquals(((Number) user.get("id")).intValue(), expectedId, "Requested user identity");
        for (String key : List.of("name", "username", "email")) {
            Assert.assertTrue(user.get(key) instanceof String && !((String) user.get(key)).isBlank(),
                    key + " must contain the returned user's data");
        }
    }

    @Test(description = "API-TC-001: direct lookup returns the selected user identity")
    public void API_TC_001_directLookupMatchesRequestedUser() throws Exception {
        Map<String, Object> user = get("API-TC-001", "direct", "/users/" + userId, null).jsonPath().getMap("");
        assertIdentity(user, userId);
    }

    @Test(description = "API-TC-002: existing-ID filter agrees with direct lookup")
    public void API_TC_002_existingFilterMatchesDirectLookup() throws Exception {
        Map<String, Object> direct = get("API-TC-002", "direct", "/users/" + userId, null).jsonPath().getMap("");
        assertIdentity(direct, userId);
        List<Map<String, Object>> filtered = get("API-TC-002", "filtered", "/users", userId).jsonPath().getList("");
        Assert.assertEquals(filtered.size(), 1, "One exact matching user is expected");
        assertIdentity(filtered.get(0), userId);
        for (String key : List.of("id", "name", "username", "email")) {
            Assert.assertEquals(filtered.get(0).get(key), direct.get(key), "Filter must preserve " + key);
        }
    }

    @Test(description = "API-TC-003: a known-absent ID filter returns no users")
    public void API_TC_003_absentIdFilterReturnsEmptyCollection() throws Exception {
        List<Map<String, Object>> users = get("API-TC-003", "collection", "/users", null).jsonPath().getList("");
        Assert.assertFalse(users.isEmpty(), "Need a collection from which to select a missing ID");
        int maximum = 0;
        for (Map<String, Object> user : users) {
            Assert.assertTrue(user.get("id") instanceof Number, "Collection IDs must be numeric");
            maximum = Math.max(maximum, ((Number) user.get("id")).intValue());
        }
        Assert.assertTrue(maximum < Integer.MAX_VALUE, "Choose a missing ID without overflow");
        int missingId = maximum + 1;
        for (Map<String, Object> user : users) {
            Assert.assertNotEquals(((Number) user.get("id")).intValue(), missingId, "Negative fixture must be absent");
        }
        List<?> absent = get("API-TC-003", "absent-filter", "/users", missingId).jsonPath().getList("");
        Assert.assertTrue(absent.isEmpty(), "Filtering a known-absent ID must not return another user");
    }
}
