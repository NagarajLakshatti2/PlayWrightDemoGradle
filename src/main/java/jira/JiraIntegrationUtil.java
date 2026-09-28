package jira;

import utils.McpToolRegistry;
import utils.McpToolResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for integrating with Jira via MCP to read requirements and test cases
 */
public class JiraIntegrationUtil {
    private static final Logger logger = LoggerFactory.getLogger(JiraIntegrationUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final McpToolRegistry mcpToolRegistry;

    public JiraIntegrationUtil() {
        this.mcpToolRegistry = new McpToolRegistry();
    }

    /**
     * Search for test requirements in Jira using JQL
     */
    public List<JiraTestRequirement> searchTestRequirements(String jqlQuery) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("jql", jqlQuery);
            params.put("max_results", 50);

            McpToolResult result = mcpToolRegistry.callTool("jira", "jira_search_issues", params);

            if (result.isSuccess()) {
                Map<String, Object> response = objectMapper.readValue(result.getOutput(), new TypeReference<Map<String, Object>>() {});
                List<Map<String, Object>> issues = (List<Map<String, Object>>) response.get("issues");

                List<JiraTestRequirement> requirements = new ArrayList<>();
                for (Map<String, Object> issue : issues) {
                    requirements.add(parseJiraIssueToRequirement(issue));
                }

                logger.info("Found {} test requirements from Jira", requirements.size());
                return requirements;
            } else {
                logger.error("Failed to search Jira: {}", result.getError());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error("Error searching Jira requirements", e);
            return new ArrayList<>();
        }
    }

    /**
     * Get a specific test requirement by issue key
     */
    public JiraTestRequirement getTestRequirement(String issueKey) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("issue_id_or_key", issueKey);

            McpToolResult result = mcpToolRegistry.callTool("jira", "jira_get_issue", params);

            if (result.isSuccess()) {
                Map<String, Object> issue = objectMapper.readValue(result.getOutput(), new TypeReference<Map<String, Object>>() {});
                return parseJiraIssueToRequirement(issue);
            } else {
                logger.error("Failed to get Jira issue {}: {}", issueKey, result.getError());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error getting Jira requirement", e);
            return null;
        }
    }

    /**
     * Get test cases from a specific Jira project
     */
    public List<JiraTestRequirement> getTestCasesFromProject(String projectKey) {
        String jql = String.format("project = %s AND issuetype in (Test, 'Test Case') ORDER BY created DESC", projectKey);
        return searchTestRequirements(jql);
    }

    /**
     * Get requirements with specific labels
     */
    public List<JiraTestRequirement> getRequirementsByLabels(List<String> labels) {
        String labelsQuery = String.join("\" OR labels = \"", labels);
        String jql = String.format("labels in (\"%s\") ORDER BY priority DESC", labelsQuery);
        return searchTestRequirements(jql);
    }

    /**
     * Parse Jira issue response to JiraTestRequirement object
     */
    private JiraTestRequirement parseJiraIssueToRequirement(Map<String, Object> issue) {
        JiraTestRequirement requirement = new JiraTestRequirement();

        try {
            Map<String, Object> fields = (Map<String, Object>) issue.get("fields");

            requirement.setIssueKey((String) issue.get("key"));
            requirement.setSummary((String) fields.get("summary"));
            requirement.setDescription((String) fields.get("description"));

            // Status
            Map<String, Object> status = (Map<String, Object>) fields.get("status");
            requirement.setStatus((String) status.get("name"));

            // Priority
            Map<String, Object> priority = (Map<String, Object>) fields.get("priority");
            if (priority != null) {
                requirement.setPriority((String) priority.get("name"));
            }

            // Assignee
            Map<String, Object> assignee = (Map<String, Object>) fields.get("assignee");
            if (assignee != null) {
                requirement.setAssignee((String) assignee.get("displayName"));
            }

            // Labels
            requirement.setLabels((List<String>) fields.get("labels"));

            // Custom fields
            requirement.setCustomFields(fields);

            // Parse acceptance criteria from description or custom field
            requirement.setAcceptanceCriteria(extractAcceptanceCriteria(requirement.getDescription()));

            // Parse test steps if available in custom fields
            requirement.setTestSteps(extractTestSteps(fields));

        } catch (Exception e) {
            logger.error("Error parsing Jira issue to requirement", e);
        }

        return requirement;
    }

    /**
     * Extract acceptance criteria from description
     */
    private List<String> extractAcceptanceCriteria(String description) {
        List<String> criteria = new ArrayList<>();
        if (description == null) return criteria;

        String[] lines = description.split("\n");
        boolean inCriteriaSection = false;

        for (String line : lines) {
            if (line.toLowerCase().contains("acceptance criteria") || line.toLowerCase().contains("ac:")) {
                inCriteriaSection = true;
                continue;
            }

            if (inCriteriaSection) {
                if (line.trim().startsWith("-") || line.trim().startsWith("*") || line.matches("^\\d+\\..*")) {
                    criteria.add(line.trim().replaceAll("^[-*\\d+.]\\s*", ""));
                } else if (line.trim().isEmpty()) {
                    inCriteriaSection = false;
                }
            }
        }

        return criteria;
    }

    /**
     * Extract test steps from custom fields
     */
    private List<JiraTestRequirement.TestStep> extractTestSteps(Map<String, Object> fields) {
        List<JiraTestRequirement.TestStep> steps = new ArrayList<>();

        // Try to get test steps from common custom field names
        String[] stepFieldNames = {"Test Steps", "Steps", "teststeps", "customfield_10100"};

        for (String fieldName : stepFieldNames) {
            Object stepsObj = fields.get(fieldName);
            if (stepsObj != null) {
                // Parse steps based on the structure
                if (stepsObj instanceof List) {
                    List<Map<String, Object>> stepsList = (List<Map<String, Object>>) stepsObj;
                    for (int i = 0; i < stepsList.size(); i++) {
                        Map<String, Object> stepData = stepsList.get(i);
                        JiraTestRequirement.TestStep step = new JiraTestRequirement.TestStep(
                            i + 1,
                            (String) stepData.get("action"),
                            (String) stepData.get("expected"),
                            (String) stepData.get("data")
                        );
                        steps.add(step);
                    }
                }
                break;
            }
        }

        return steps;
    }

    /**
     * Update test execution status in Jira
     */
    public boolean updateTestExecution(String issueKey, String status, String comment) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("issue_id_or_key", issueKey);
            params.put("transition", Map.of("name", status));

            McpToolResult result = mcpToolRegistry.callTool("jira", "jira_transition_issue", params);

            if (result.isSuccess()) {
                // Add comment
                if (comment != null && !comment.isEmpty()) {
                    Map<String, Object> commentParams = new HashMap<>();
                    commentParams.put("issue_id_or_key", issueKey);
                    commentParams.put("body", comment);

                    mcpToolRegistry.callTool("jira", "jira_add_comment", commentParams);
                }

                logger.info("Updated test execution status for {} to {}", issueKey, status);
                return true;
            } else {
                logger.error("Failed to update test execution status: {}", result.getError());
                return false;
            }
        } catch (Exception e) {
            logger.error("Error updating test execution status", e);
            return false;
        }
    }

    /**
     * Create a new test case in Jira
     */
    public String createTestCase(String projectKey, String summary, String description, List<String> labels) {
        try {
            Map<String, Object> fields = new HashMap<>();
            fields.put("project", Map.of("key", projectKey));
            fields.put("summary", summary);
            fields.put("description", description);
            fields.put("issuetype", Map.of("name", "Test"));
            fields.put("labels", labels);

            Map<String, Object> params = new HashMap<>();
            params.put("fields", fields);

            McpToolResult result = mcpToolRegistry.callTool("jira", "jira_create_issue", params);

            if (result.isSuccess()) {
                Map<String, Object> response = objectMapper.readValue(result.getOutput(), new TypeReference<Map<String, Object>>() {});
                String issueKey = (String) response.get("key");
                logger.info("Created test case: {}", issueKey);
                return issueKey;
            } else {
                logger.error("Failed to create test case: {}", result.getError());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error creating test case", e);
            return null;
        }
    }
}