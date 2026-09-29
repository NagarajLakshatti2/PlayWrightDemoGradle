package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Jira REST API client for updating test case status
 * Uses REST Assured for HTTP requests
 */
public class JiraApiClient {

    private static final Logger log = LoggerFactory.getLogger(JiraApiClient.class);
    private static final String JIRA_BASE_URL = "https://nagarajlakshatti11.atlassian.net";
    private static final String JIRA_API_PATH = "/rest/api/3";

    private final String email;
    private final String apiToken;

    public JiraApiClient(String email, String apiToken) {
        this.email = email;
        this.apiToken = apiToken;
        RestAssured.baseURI = JIRA_BASE_URL;
    }

    /**
     * Update Jira issue status using a transition
     * 
     * @param issueKey     The Jira issue key (e.g., QAP-3)
     * @param targetStatus The target status name (e.g., "PASS", "FAIL")
     * @return Response from Jira API
     */
    public io.restassured.response.Response updateIssueStatus(String issueKey, String targetStatus) {
        log.info("Updating issue {} to status: {}", issueKey, targetStatus);

        try {
            // First, get available transitions for the issue
            Response transitionsResponse = getTransitions(issueKey);

            if (transitionsResponse.getStatusCode() != 200) {
                log.error("Failed to get transitions for issue {}: {}", issueKey, transitionsResponse.getStatusLine());
                return transitionsResponse;
            }

            // Find the transition ID for the target status
            String transitionId = findTransitionId(transitionsResponse, targetStatus);

            if (transitionId == null) {
                log.warn("Transition to status '{}' not found for issue {}. Available transitions:", targetStatus,
                        issueKey);
                log.warn("Please ensure the status '{}' exists in your Jira workflow", targetStatus);
                return transitionsResponse;
            }

            // Execute the transition
            return executeTransition(issueKey, transitionId);

        } catch (Exception e) {
            log.error("Error updating status for issue {}", issueKey, e);
            throw new RuntimeException("Failed to update Jira status", e);
        }
    }

    /**
     * Get available transitions for an issue
     */
    private io.restassured.response.Response getTransitions(String issueKey) {
        return RestAssured.given()
                .auth().preemptive().basic(email, apiToken)
                .contentType("application/json")
                .get(JIRA_API_PATH + "/issue/" + issueKey + "/transitions");
    }

    /**
     * Find transition ID for a target status name
     */
    private String findTransitionId(Response transitionsResponse, String targetStatus) {
        try {
            String transitionsJson = transitionsResponse.asString();
            @SuppressWarnings("unchecked")
            Map<String, Object> response = new com.fasterxml.jackson.databind.ObjectMapper().readValue(transitionsJson,
                    Map.class);

            @SuppressWarnings("unchecked")
            java.util.List<Map<String, Object>> transitionList = (java.util.List<Map<String, Object>>) response
                    .get("transitions");

            if (transitionList == null) {
                return null;
            }

            for (Map<String, Object> transition : transitionList) {
                Map<String, Object> to = (Map<String, Object>) transition.get("to");
                if (to != null) {
                    String statusName = (String) to.get("name");
                    if (targetStatus.equalsIgnoreCase(statusName)) {
                        return (String) transition.get("id");
                    }
                }
            }

            // Log available transitions for debugging
            logAvailableTransitions(transitionList);

            return null;

        } catch (Exception e) {
            log.error("Error parsing transitions response", e);
            return null;
        }
    }

    /**
     * Log available transitions for debugging
     */
    private void logAvailableTransitions(java.util.List<Map<String, Object>> transitionList) {
        log.warn("Available transitions for this issue:");
        for (Map<String, Object> transition : transitionList) {
            Map<String, Object> to = (Map<String, Object>) transition.get("to");
            if (to != null) {
                String statusName = (String) to.get("name");
                String transitionId = (String) transition.get("id");
                log.warn("  - {} (ID: {})", statusName, transitionId);
            }
        }
    }

    /**
     * Execute a transition on an issue
     */
    private Response executeTransition(String issueKey, String transitionId) {
        String requestBody = String.format("{\"transition\": {\"id\": \"%s\"}}", transitionId);

        return RestAssured.given()
                .auth().preemptive().basic(email, apiToken)
                .contentType("application/json")
                .body(requestBody)
                .post(JIRA_API_PATH + "/issue/" + issueKey + "/transitions");
    }

    /**
     * Get issue details
     */
    public Response getIssue(String issueKey) {
        return RestAssured.given()
                .auth().preemptive().basic(email, apiToken)
                .contentType("application/json")
                .get(JIRA_API_PATH + "/issue/" + issueKey);
    }

    /**
     * Search issues using JQL
     */
    public Response searchIssues(String jql) {
        String requestBody = String.format("{\"jql\": \"%s\", \"maxResults\": 50}", jql.replace("\"", "\\\""));

        return RestAssured.given()
                .auth().preemptive().basic(email, apiToken)
                .contentType("application/json")
                .body(requestBody)
                .post(JIRA_API_PATH + "/search");
    }
}
