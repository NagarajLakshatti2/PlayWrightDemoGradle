package runners;

import com.fasterxml.jackson.databind.ObjectMapper;
import jira.JiraIntegrationUtil;
import jira.JiraTestRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Runner for automating test cases with approval mechanism.
 * 
 * This runner:
 * 1. Fetches test cases with status "TO BE AUTOMATE" from Jira
 * 2. Filters by approved IDs in automation-approval.json
 * 3. Displays Epic, Story, Subtask, Title, and Jira URL
 * 4. Automates only approved test cases
 */
public class AutomationApprovalRunner {

    private static final Logger log = LoggerFactory.getLogger(AutomationApprovalRunner.class);
    private static final String APPROVAL_CONFIG_PATH = "src/main/resources/config/automation-approval.json";
    private static final String JIRA_BASE_URL = "https://nagarajlakshatti11.atlassian.net/browse/";
    private static final String PROJECT_KEY = "QAP";

    public static void main(String[] args) {
        log.info("===========================================");
        log.info("Automation Approval Runner");
        log.info("===========================================");

        try {
            // Load approved test cases from config
            Set<String> approvedIds = loadApprovedTestCases();
            log.info("Loaded {} approved test case IDs", approvedIds.size());

            // Fetch all test cases with "TO BE AUTOMATE" status
            List<Map<String, Object>> toAutomateCases = fetchToAutomateTestCases();
            log.info("Found {} test cases with 'TO BE AUTOMATE' status", toAutomateCases.size());

            // Filter by approved IDs
            List<Map<String, Object>> approvedCases = filterApprovedCases(toAutomateCases, approvedIds);
            List<Map<String, Object>> skippedCases = new ArrayList<>(toAutomateCases);
            skippedCases.removeAll(approvedCases);

            // Display approved cases
            displayTestCases("Approved for Automation", approvedCases, true);

            // Display skipped cases
            if (!skippedCases.isEmpty()) {
                displayTestCases("Skipped (Not Approved)", skippedCases, false);
            }

            // Automation would happen here
            log.info("===========================================");
            log.info("Summary:");
            log.info("  Approved: {}", approvedCases.size());
            log.info("  Skipped: {}", skippedCases.size());
            log.info("===========================================");

            if (!approvedCases.isEmpty()) {
                log.info("Ready to automate {} approved test cases", approvedCases.size());
                log.info("Note: Actual automation implementation to be added");
            }

        } catch (Exception e) {
            log.error("Error in Automation Approval Runner", e);
            log.error("Note: This may be due to Jira MCP not being available. Using mock data for demonstration.");
            e.printStackTrace();
        }
    }

    /**
     * Load approved test case IDs from configuration file
     */
    private static Set<String> loadApprovedTestCases() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File configFile = new File(APPROVAL_CONFIG_PATH);

        if (!configFile.exists()) {
            log.warn("Approval config file not found: {}", APPROVAL_CONFIG_PATH);
            log.warn("Creating empty approval list");
            return new HashSet<>();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> config = mapper.readValue(configFile, Map.class);
        @SuppressWarnings("unchecked")
        List<String> approvedList = (List<String>) config.get("approvedTestCases");

        return approvedList != null ? new HashSet<>(approvedList) : new HashSet<>();
    }

    /**
     * Fetch test cases with "TO BE AUTOMATE" status from Jira
     */
    private static List<Map<String, Object>> fetchToAutomateTestCases() {
        log.info("Fetching test cases from Jira with status 'TO BE AUTOMATE'...");

        try {
            JiraIntegrationUtil jiraUtil = new JiraIntegrationUtil();
            // JQL to find test cases with "TO BE AUTOMATE" status
            String jql = String.format("project = %s AND status = 'TO BE AUTOMATE' AND issuetype = Subtask", PROJECT_KEY);
            
            List<JiraTestRequirement> requirements = jiraUtil.searchTestRequirements(jql);
            
            // Convert to map format - using available fields only
            List<Map<String, Object>> cases = new ArrayList<>();
            for (JiraTestRequirement req : requirements) {
                Map<String, Object> testCase = new LinkedHashMap<>();
                testCase.put("epicId", "N/A"); // Not available in current JiraTestRequirement
                testCase.put("epicTitle", "N/A");
                testCase.put("storyId", "N/A");
                testCase.put("storyTitle", "N/A");
                testCase.put("subtaskId", req.getIssueKey());
                testCase.put("subtaskTitle", req.getSummary());
                testCase.put("status", req.getStatus());
                cases.add(testCase);
            }
            
            return cases;
            
        } catch (Exception e) {
            log.warn("Failed to fetch from Jira MCP, using mock data for demonstration");
            log.warn("To use real Jira data, ensure Jira MCP is properly configured");
            return getMockTestCases();
        }
    }

    /**
     * Mock test cases for demonstration when Jira MCP is not available
     */
    private static List<Map<String, Object>> getMockTestCases() {
        List<Map<String, Object>> mockCases = new ArrayList<>();

        Map<String, Object> case1 = new LinkedHashMap<>();
        case1.put("epicId", "QAP-1");
        case1.put("epicTitle", "Text Input Module");
        case1.put("storyId", "QAP-2");
        case1.put("storyTitle", "Basic Text Input Functionality");
        case1.put("subtaskId", "QAP-3");
        case1.put("subtaskTitle", "Verify Text Input Field Functionality");
        case1.put("status", "TO BE AUTOMATE");
        mockCases.add(case1);

        Map<String, Object> case2 = new LinkedHashMap<>();
        case2.put("epicId", "QAP-1");
        case2.put("epicTitle", "Text Input Module");
        case2.put("storyId", "QAP-4");
        case2.put("storyTitle", "Email Input Validation");
        case2.put("subtaskId", "QAP-5");
        case2.put("subtaskTitle", "Verify Email Input Validation");
        case2.put("status", "TO BE AUTOMATE");
        mockCases.add(case2);

        return mockCases;
    }

    /**
     * Filter test cases by approved IDs
     */
    private static List<Map<String, Object>> filterApprovedCases(
            List<Map<String, Object>> toAutomateCases,
            Set<String> approvedIds) {

        return toAutomateCases.stream()
                .filter(testCase -> approvedIds.contains(testCase.get("subtaskId")))
                .collect(Collectors.toList());
    }

    /**
     * Display test cases in a formatted table
     */
    private static void displayTestCases(String title, List<Map<String, Object>> cases, boolean showDetails) {
        if (cases.isEmpty()) {
            log.info("{}: None", title);
            return;
        }

        log.info("");
        log.info("===========================================");
        log.info("{}", title);
        log.info("===========================================");

        // Header
        log.info(String.format("%-10s %-10s %-10s %-40s %s",
                "Epic", "Story", "Subtask", "Title", "Jira URL"));

        for (Map<String, Object> testCase : cases) {
            String epicId = (String) testCase.get("epicId");
            String storyId = (String) testCase.get("storyId");
            String subtaskId = (String) testCase.get("subtaskId");
            String titleText = (String) testCase.get("subtaskTitle");
            String jiraUrl = JIRA_BASE_URL + subtaskId;

            log.info(String.format("%-10s %-10s %-10s %-40s %s",
                    epicId, storyId, subtaskId, truncate(titleText, 40), jiraUrl));
        }

        log.info("===========================================");
    }

    /**
     * Truncate string to specified length
     */
    private static String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}
