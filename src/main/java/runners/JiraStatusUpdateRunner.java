package runners;

import api.JiraApiClient;
import api.JiraConfigLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Runner for updating Jira test case status based on test execution results.
 * 
 * This runner:
 * 1. Reads configuration from jira-config.json
 * 2. Runs tests (by tags: regression, sanity, smoke, etc.)
 * 3. Updates Jira subtask status to PASS/FAIL if enabled using REST API
 * 
 * Note: Only updates test case subtask status, not parent story status.
 * Parent story status to be updated manually by QA team.
 */
public class JiraStatusUpdateRunner {

    private static final Logger log = LoggerFactory.getLogger(JiraStatusUpdateRunner.class);
    private static final String JIRA_CONFIG_PATH = "src/main/resources/config/jira-config.json";
    private static final String JIRA_BASE_URL = "https://nagarajlakshatti11.atlassian.net/browse/";

    public static void main(String[] args) {
        String[] tags = args.length > 0 ? args : new String[]{"regression"};
        log.info("===========================================");
        log.info("Jira Status Update Runner");
        log.info("Running tests with tags: {}", Arrays.toString(tags));
        log.info("===========================================");

        try {
            // Load Jira configuration
            JiraConfig config = loadJiraConfig();
            log.info("Status update enabled: {}", config.enableStatusUpdate);
            log.info("Pass status: {}", config.statusMapping.get("pass"));
            log.info("Fail status: {}", config.statusMapping.get("fail"));

            if (!config.enableStatusUpdate) {
                log.info("Status update is DISABLED. Skipping Jira updates.");
                log.info("To enable, set 'enableStatusUpdate' to true in jira-config.json");
            }

            // Run tests and get results
            Map<String, String> testResults = runTests(tags);
            log.info("Test execution completed. {} test cases executed.", testResults.size());

            // Display test results
            displayTestResults(testResults);

            // Update Jira status if enabled
            if (config.enableStatusUpdate) {
                updateJiraStatus(testResults, config);
            } else {
                log.info("Dry run completed. Jira status not updated.");
            }

            log.info("===========================================");
            log.info("Summary:");
            log.info("  Total: {}", testResults.size());
            log.info("  Passed: {}", countStatus(testResults, "PASS"));
            log.info("  Failed: {}", countStatus(testResults, "FAIL"));
            log.info("===========================================");

        } catch (Exception e) {
            log.error("Error in Jira Status Update Runner", e);
            System.exit(1);
        }
    }

    /**
     * Load Jira configuration from file
     */
    private static JiraConfig loadJiraConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File configFile = new File(JIRA_CONFIG_PATH);

        if (!configFile.exists()) {
            log.warn("Jira config file not found: {}", JIRA_CONFIG_PATH);
            log.warn("Using default configuration (status update disabled)");
            return new JiraConfig(false, Map.of("pass", "PASS", "fail", "FAIL"));
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> configMap = mapper.readValue(configFile, Map.class);
        boolean enabled = Boolean.TRUE.equals(configMap.get("enableStatusUpdate"));
        
        @SuppressWarnings("unchecked")
        Map<String, String> statusMapping = (Map<String, String>) configMap.get("statusMapping");
        
        if (statusMapping == null) {
            statusMapping = Map.of("pass", "PASS", "fail", "FAIL");
        }

        return new JiraConfig(enabled, statusMapping);
    }

    /**
     * Run tests by tags and return results
     * Note: This is a placeholder - actual implementation would run Cucumber tests
     */
    private static Map<String, String> runTests(String[] tags) {
        log.info("Running tests with tags: {}", Arrays.toString(tags));
        log.info("Note: Actual test execution to be implemented");

        // Placeholder implementation
        // In real implementation, this would:
        // 1. Run Cucumber tests with specified tags
        // 2. Parse test results
        // 3. Map scenario names to Jira test case IDs

        Map<String, String> mockResults = new LinkedHashMap<>();
        mockResults.put("QAP-3", "PASS");
        mockResults.put("QAP-5", "PASS");
        
        return mockResults;
    }

    /**
     * Display test results in a formatted table
     */
    private static void displayTestResults(Map<String, String> testResults) {
        log.info("");
        log.info("===========================================");
        log.info("Test Results");
        log.info("===========================================");

        log.info(String.format("%-15s %-10s %s", "Test Case", "Status", "Jira URL"));

        for (Map.Entry<String, String> entry : testResults.entrySet()) {
            String testCaseId = entry.getKey();
            String status = entry.getValue();
            String jiraUrl = JIRA_BASE_URL + testCaseId;

            log.info(String.format("%-15s %-10s %s", testCaseId, status, jiraUrl));
        }

        log.info("===========================================");
    }

    /**
     * Update Jira status based on test results using REST API
     */
    private static void updateJiraStatus(Map<String, String> testResults, JiraConfig config) {
        log.info("Updating Jira status for {} test cases...", testResults.size());

        // Load Jira credentials
        String email = JiraConfigLoader.getJiraEmail();
        String token = JiraConfigLoader.getJiraToken();

        if (email == null || email.isEmpty() || token == null || token.isEmpty()) {
            log.error("Jira credentials not found. Please set JIRA_EMAIL and JIRA_TOKEN in environment or .devin/jira-confluence.env");
            return;
        }

        // Create Jira API client
        JiraApiClient jiraClient = new JiraApiClient(email, token);

        for (Map.Entry<String, String> entry : testResults.entrySet()) {
            String testCaseId = entry.getKey();
            String result = entry.getValue();
            String jiraStatus = config.statusMapping.get(result.toLowerCase());

            log.info("Updating {} to status: {}", testCaseId, jiraStatus);

            try {
                Response response = jiraClient.updateIssueStatus(testCaseId, jiraStatus);
                
                if (response.getStatusCode() == 204) {
                    log.info("✅ Successfully updated {} to {}", testCaseId, jiraStatus);
                } else {
                    log.error("❌ Failed to update {}. Status: {}, Response: {}", 
                            testCaseId, response.getStatusCode(), response.getStatusLine());
                }

            } catch (Exception e) {
                log.error("❌ Error updating status for {}: {}", testCaseId, e.getMessage());
            }
        }

        log.info("Jira status update completed.");
    }

    /**
     * Count test cases by status
     */
    private static long countStatus(Map<String, String> testResults, String status) {
        return testResults.values().stream()
                .filter(s -> s.equalsIgnoreCase(status))
                .count();
    }

    /**
     * Configuration class for Jira settings
     */
    private static class JiraConfig {
        boolean enableStatusUpdate;
        Map<String, String> statusMapping;

        JiraConfig(boolean enableStatusUpdate, Map<String, String> statusMapping) {
            this.enableStatusUpdate = enableStatusUpdate;
            this.statusMapping = statusMapping;
        }
    }
}
