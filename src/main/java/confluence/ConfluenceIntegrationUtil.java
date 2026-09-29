package confluence;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for integrating with Confluence via MCP to read requirements and test data
 */
public class ConfluenceIntegrationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfluenceIntegrationUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final McpToolRegistry mcpToolRegistry;

    public ConfluenceIntegrationUtil() {
        this.mcpToolRegistry = new McpToolRegistry();
    }

    /**
     * Search for requirements in Confluence using CQL
     */
    public List<ConfluenceRequirement> searchRequirements(String cqlQuery) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("cql", cqlQuery);
            params.put("limit", 50);

            McpToolResult result = mcpToolRegistry.callTool("confluence", "confluence_search", params);

            if (result.isSuccess()) {
                Map<String, Object> response = objectMapper.readValue(result.getOutput(), new TypeReference<Map<String, Object>>() {});
                List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

                List<ConfluenceRequirement> requirements = new ArrayList<>();
                for (Map<String, Object> page : results) {
                    requirements.add(parseConfluencePageToRequirement(page));
                }

                logger.info("Found {} requirements from Confluence", requirements.size());
                return requirements;
            } else {
                logger.error("Failed to search Confluence: {}", result.getError());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error("Error searching Confluence requirements", e);
            return new ArrayList<>();
        }
    }

    /**
     * Get a specific requirement page by ID
     */
    public ConfluenceRequirement getRequirement(String pageId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("page_id", pageId);

            McpToolResult result = mcpToolRegistry.callTool("confluence", "confluence_get_page", params);

            if (result.isSuccess()) {
                Map<String, Object> page = objectMapper.readValue(result.getOutput(), new TypeReference<Map<String, Object>>() {});
                return parseConfluencePageToRequirement(page);
            } else {
                logger.error("Failed to get Confluence page {}: {}", pageId, result.getError());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error getting Confluence requirement", e);
            return null;
        }
    }

    /**
     * Get all pages from a specific space
     */
    public List<ConfluenceRequirement> getPagesFromSpace(String spaceKey) {
        String cql = String.format("space = %s AND type = page ORDER BY created DESC", spaceKey);
        return searchRequirements(cql);
    }

    /**
     * Get requirements with specific labels
     */
    public List<ConfluenceRequirement> getRequirementsByLabels(List<String> labels) {
        String labelsQuery = String.join("\" OR label = \"", labels);
        String cql = String.format("label in (\"%s\") ORDER BY created DESC", labelsQuery);
        return searchRequirements(cql);
    }

    /**
     * Get test data pages from Confluence
     */
    public List<ConfluenceRequirement> getTestDataPages(String spaceKey) {
        String cql = String.format("space = %s AND label = \"test-data\" ORDER BY title", spaceKey);
        return searchRequirements(cql);
    }

    /**
     * Parse Confluence page response to ConfluenceRequirement object
     */
    private ConfluenceRequirement parseConfluencePageToRequirement(Map<String, Object> page) {
        ConfluenceRequirement requirement = new ConfluenceRequirement();

        try {
            requirement.setPageId((String) page.get("id"));
            requirement.setTitle((String) page.get("title"));

            // Extract content (Confluence stores content in storage format)
            Map<String, Object> body = (Map<String, Object>) page.get("body");
            if (body != null) {
                Map<String, Object> storage = (Map<String, Object>) body.get("storage");
                if (storage != null) {
                    requirement.setContent((String) storage.get("value"));
                }
            }

            // Space information
            Map<String, Object> space = (Map<String, Object>) page.get("space");
            if (space != null) {
                requirement.setSpaceKey((String) space.get("key"));
                requirement.setSpaceName((String) space.get("name"));
            }

            // Author and modification info
            Map<String, Object> history = (Map<String, Object>) page.get("history");
            if (history != null) {
                Map<String, Object> createdBy = (Map<String, Object>) history.get("createdBy");
                if (createdBy != null) {
                    requirement.setAuthor((String) createdBy.get("displayName"));
                }
                requirement.setLastModified((String) history.get("lastUpdated"));
                requirement.setVersion(String.valueOf(history.get("version")));
            }

            // Labels
            Map<String, Object> metadata = (Map<String, Object>) page.get("metadata");
            if (metadata != null) {
                Map<String, Object> labelsData = (Map<String, Object>) metadata.get("labels");
                if (labelsData != null) {
                    List<Map<String, Object>> labelList = (List<Map<String, Object>>) labelsData.get("results");
                    List<String> labelNames = new ArrayList<>();
                    for (Map<String, Object> label : labelList) {
                        labelNames.add((String) label.get("name"));
                    }
                    requirement.setLabels(labelNames);
                }
                requirement.setMetadata(metadata);
            }

            // Extract test cases and test data from content
            requirement.setTestCases(extractTestCases(requirement.getContent()));
            requirement.setTestData(extractTestData(requirement.getContent()));

            // Determine requirement type from labels or content
            requirement.setRequirementType(determineRequirementType(requirement.getLabels(), requirement.getContent()));

        } catch (Exception e) {
            logger.error("Error parsing Confluence page to requirement", e);
        }

        return requirement;
    }

    /**
     * Extract test cases from Confluence content
     */
    private List<String> extractTestCases(String content) {
        List<String> testCases = new ArrayList<>();
        if (content == null) return testCases;

        // Look for test case tables or structured content
        Pattern tablePattern = Pattern.compile("<table[^>]*>.*?</table>", Pattern.DOTALL);
        Matcher tableMatcher = tablePattern.matcher(content);

        while (tableMatcher.find()) {
            String table = tableMatcher.group();
            // Extract test case data from table rows
            Pattern rowPattern = Pattern.compile("<tr[^>]*>.*?</tr>", Pattern.DOTALL);
            Matcher rowMatcher = rowPattern.matcher(table);

            while (rowMatcher.find()) {
                String row = rowMatcher.group().replaceAll("<[^>]+>", " ").trim();
                if (!row.isEmpty() && !row.toLowerCase().contains("test case") && !row.toLowerCase().contains("step")) {
                    testCases.add(row);
                }
            }
        }

        // Also look for numbered lists or bullet points that might be test cases
        Pattern listPattern = Pattern.compile("<li[^>]*>(.*?)</li>", Pattern.DOTALL);
        Matcher listMatcher = listPattern.matcher(content);

        while (listMatcher.find()) {
            String item = listMatcher.group(1).replaceAll("<[^>]+>", "").trim();
            if (item.length() > 10) { // Filter out very short items
                testCases.add(item);
            }
        }

        return testCases;
    }

    /**
     * Extract test data from Confluence content
     */
    private List<String> extractTestData(String content) {
        List<String> testData = new ArrayList<>();
        if (content == null) return testData;

        // Look for JSON data blocks
        Pattern jsonPattern = Pattern.compile("<ac:structured-macro[^>]*name=\"code\"[^>]*>.*?</ac:structured-macro>", Pattern.DOTALL);
        Matcher jsonMatcher = jsonPattern.matcher(content);

        while (jsonMatcher.find()) {
            String codeBlock = jsonMatcher.group();
            if (codeBlock.contains("{") || codeBlock.contains("[")) {
                // Extract the actual code content
                Pattern contentPattern = Pattern.compile("<ac:plain-text-body><\\!\\[CDATA\\[(.*?)\\]\\]></ac:plain-text-body>", Pattern.DOTALL);
                Matcher contentMatcher = contentPattern.matcher(codeBlock);
                if (contentMatcher.find()) {
                    String jsonData = contentMatcher.group(1).trim();
                    testData.add(jsonData);
                }
            }
        }

        // Look for data tables (usually have "data" or "test data" in headers)
        Pattern tablePattern = Pattern.compile("<table[^>]*>.*?</table>", Pattern.DOTALL);
        Matcher tableMatcher = tablePattern.matcher(content);

        while (tableMatcher.find()) {
            String table = tableMatcher.group();
            if (table.toLowerCase().contains("data") || table.toLowerCase().contains("input") || table.toLowerCase().contains("expected")) {
                testData.add(table.replaceAll("<[^>]+>", " ").trim());
            }
        }

        return testData;
    }

    /**
     * Determine requirement type from labels and content
     */
    private String determineRequirementType(List<String> labels, String content) {
        if (labels != null) {
            for (String label : labels) {
                if (label.toLowerCase().contains("functional")) return "functional";
                if (label.toLowerCase().contains("non-functional")) return "non-functional";
                if (label.toLowerCase().contains("business")) return "business";
                if (label.toLowerCase().contains("security")) return "security";
                if (label.toLowerCase().contains("performance")) return "performance";
            }
        }

        if (content != null) {
            String lowerContent = content.toLowerCase();
            if (lowerContent.contains("functional requirement")) return "functional";
            if (lowerContent.contains("non-functional")) return "non-functional";
            if (lowerContent.contains("business requirement")) return "business";
            if (lowerContent.contains("security requirement")) return "security";
            if (lowerContent.contains("performance")) return "performance";
        }

        return "general";
    }

    /**
     * Create a new test report page in Confluence
     */
    public String createTestReport(String spaceKey, String title, String content, List<String> labels) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("space_key", spaceKey);
            params.put("title", title);
            params.put("content", content);

            if (labels != null && !labels.isEmpty()) {
                params.put("labels", labels);
            }

            McpToolResult result = mcpToolRegistry.callTool("confluence", "confluence_create_page", params);

            if (result.isSuccess()) {
                Map<String, Object> response = objectMapper.readValue(result.getOutput(), new TypeReference<Map<String, Object>>() {});
                String pageId = (String) response.get("id");
                logger.info("Created test report page: {}", pageId);
                return pageId;
            } else {
                logger.error("Failed to create test report: {}", result.getError());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error creating test report", e);
            return null;
        }
    }

    /**
     * Update an existing page in Confluence
     */
    public boolean updatePage(String pageId, String content) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("page_id", pageId);
            params.put("content", content);

            McpToolResult result = mcpToolRegistry.callTool("confluence", "confluence_update_page", params);

            if (result.isSuccess()) {
                logger.info("Updated Confluence page: {}", pageId);
                return true;
            } else {
                logger.error("Failed to update Confluence page: {}", result.getError());
                return false;
            }
        } catch (Exception e) {
            logger.error("Error updating Confluence page", e);
            return false;
        }
    }

    /**
     * Add a comment to a Confluence page
     */
    public boolean addComment(String pageId, String comment) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("page_id", pageId);
            params.put("body", comment);

            McpToolResult result = mcpToolRegistry.callTool("confluence", "confluence_add_comment", params);

            if (result.isSuccess()) {
                logger.info("Added comment to Confluence page: {}", pageId);
                return true;
            } else {
                logger.error("Failed to add comment: {}", result.getError());
                return false;
            }
        } catch (Exception e) {
            logger.error("Error adding comment to Confluence page", e);
            return false;
        }
    }
}