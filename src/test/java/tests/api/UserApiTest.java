package tests.api;

import config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = ConfigReader.apiBaseUrl();
    }

    @Test(description = "GET /users/1 should return a valid user")
    public void getSingleUserReturns200() {
        Response response = given().when().get("/users/1");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), 1);
        Assert.assertNotNull(response.jsonPath().getString("email"));
    }

    @Test(description = "POST /posts should create a resource")
    public void createPostReturns201() {
        String payload = """
            {
              "title": "Playwright Java framework",
              "body": "Created via RestAssured",
              "userId": 1
            }
            """;

        Response response = given()
                .contentType("application/json")
                .body(payload)
                .when().post("/posts");

        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("title"), "Playwright Java framework");
    }
}
