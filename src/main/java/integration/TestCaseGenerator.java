package integration;

import jira.JiraIntegrationUtil;
import jira.JiraTestRequirement;
import confluence.ConfluenceIntegrationUtil;
import confluence.ConfluenceRequirement;
import utils.McpToolRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Generates test cases from Jira and Confluence requirements
 */
public class TestCaseGenerator {
    private static final Logger logger = LoggerFactory.getLogger(TestCaseGenerator.class);

    private final JiraIntegrationUtil jiraIntegration;
    private final ConfluenceIntegrationUtil confluenceIntegration;
    private final String featureFilesPath;
    private final String testDataPath;

    public TestCaseGenerator(String featureFilesPath, String testDataPath) {
        this.jiraIntegration = new JiraIntegrationUtil();
        this.confluenceIntegration = new ConfluenceIntegrationUtil();
        this.featureFilesPath = featureFilesPath;
        this.testDataPath = testDataPath;
    }

    /**
     * Generate test cases from Jira requirements
     */
    public List<String> generateFromJira(String jqlQuery) {
        List<String> generatedFiles = new ArrayList<>();

        try {
            List<JiraTestRequirement> requirements = jiraIntegration.searchTestRequirements(jqlQuery);

            for (JiraTestRequirement requirement : requirements) {
                String featureFile = generateFeatureFile(requirement);
                if (featureFile != null) {
                    generatedFiles.add(featureFile);
                }

                // Generate test data if available
                if (requirement.getTestSteps() != null && !requirement.getTestSteps().isEmpty()) {
                    String testDataFile = generateTestDataFile(requirement);
                    if (testDataFile != null) {
                        generatedFiles.add(testDataFile);
                    }
                }
            }

            logger.info("Generated {} test files from Jira requirements", generatedFiles.size());
        } catch (Exception e) {
            logger.error("Error generating test cases from Jira", e);
        }

        return generatedFiles;
    }

    /**
     * Generate test cases from Confluence requirements
     */
    public List<String> generateFromConfluence(String cqlQuery) {
        List<String> generatedFiles = new ArrayList<>();

        try {
            List<ConfluenceRequirement> requirements = confluenceIntegration.searchRequirements(cqlQuery);

            for (ConfluenceRequirement requirement : requirements) {
                String featureFile = generateFeatureFile(requirement);
                if (featureFile != null) {
                    generatedFiles.add(featureFile);
                }

                // Generate test data files if available
                if (requirement.getTestData() != null && !requirement.getTestData().isEmpty()) {
                    String testDataFile = generateTestDataFile(requirement);
                    if (testDataFile != null) {
                        generatedFiles.add(testDataFile);
                    }
                }
            }

            logger.info("Generated {} test files from Confluence requirements", generatedFiles.size());
        } catch (Exception e) {
            logger.error("Error generating test cases from Confluence", e);
        }

        return generatedFiles;
    }

    /**
     * Generate feature file from Jira requirement
     */
    private String generateFeatureFile(JiraTestRequirement requirement) {
        try {
            String sanitizedTitle = sanitizeFileName(requirement.getSummary());
            String fileName = String.format("%s.feature", sanitizedTitle);
            String filePath = Paths.get(featureFilesPath, fileName).toString();

            StringBuilder featureContent = new StringBuilder();
            featureContent.append("Feature: ").append(requirement.getSummary()).append("\n");
            featureContent.append("  As a user\n");
            featureContent.append("  I want to ").append(requirement.getSummary().toLowerCase()).append("\n");
            featureContent.append("  So that I can achieve the business objective\n\n");

            // Add metadata
            featureContent.append("  @jira.").append(requirement.getIssueKey()).append("\n");
            if (requirement.getPriority() != null) {
                featureContent.append("  @priority.").append(requirement.getPriority().toLowerCase()).append("\n");
            }
            if (requirement.getLabels() != null && !requirement.getLabels().isEmpty()) {
                for (String label : requirement.getLabels()) {
                    featureContent.append("  @").append(label).append("\n");
                }
            }
            featureContent.append("\n");

            // Add acceptance criteria as scenarios
            if (requirement.getAcceptanceCriteria() != null && !requirement.getAcceptanceCriteria().isEmpty()) {
                for (String criterion : requirement.getAcceptanceCriteria()) {
                    featureContent.append("  Scenario: ").append(criterion).append("\n");
                    featureContent.append("    Given I am on the relevant page\n");
                    featureContent.append("    When I perform the required action\n");
                    featureContent.append("    Then I should see the expected result\n\n");
                }
            }

            // Add test steps as scenarios if available
            if (requirement.getTestSteps() != null && !requirement.getTestSteps().isEmpty()) {
                for (JiraTestRequirement.TestStep step : requirement.getTestSteps()) {
                    featureContent.append("  Scenario: Test Step ").append(step.getStepNumber()).append(" - ").append(step.getAction()).append("\n");
                    featureContent.append("    Given I am on the relevant page\n");
                    featureContent.append("    When I ").append(step.getAction()).append("\n");
                    featureContent.append("    Then ").append(step.getExpectedResult()).append("\n\n");
                }
            }

            // Write feature file
            ensureDirectoryExists(featureFilesPath);
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(featureContent.toString());
            }

            logger.info("Generated feature file: {}", filePath);
            return filePath;

        } catch (Exception e) {
            logger.error("Error generating feature file from Jira requirement", e);
            return null;
        }
    }

    /**
     * Generate feature file from Confluence requirement
     */
    private String generateFeatureFile(ConfluenceRequirement requirement) {
        try {
            String sanitizedTitle = sanitizeFileName(requirement.getTitle());
            String fileName = String.format("%s.feature", sanitizedTitle);
            String filePath = Paths.get(featureFilesPath, fileName).toString();

            StringBuilder featureContent = new StringBuilder();
            featureContent.append("Feature: ").append(requirement.getTitle()).append("\n");
            featureContent.append("  As a user\n");
            featureContent.append("  I want to ").append(requirement.getTitle().toLowerCase()).append("\n");
            featureContent.append("  So that I can achieve the business objective\n\n");

            // Add metadata
            featureContent.append("  @confluence.").append(requirement.getPageId()).append("\n");
            featureContent.append("  @space.").append(requirement.getSpaceKey()).append("\n");
            if (requirement.getLabels() != null && !requirement.getLabels().isEmpty()) {
                for (String label : requirement.getLabels()) {
                    featureContent.append("  @").append(label).append("\n");
                }
            }
            featureContent.append("\n");

            // Add test cases as scenarios
            if (requirement.getTestCases() != null && !requirement.getTestCases().isEmpty()) {
                for (String testCase : requirement.getTestCases()) {
                    featureContent.append("  Scenario: ").append(testCase.substring(0, Math.min(50, testCase.length()))).append("\n");
                    featureContent.append("    Given I am on the relevant page\n");
                    featureContent.append("    When I perform the required action\n");
                    featureContent.append("    Then I should see the expected result\n\n");
                }
            }

            // Write feature file
            ensureDirectoryExists(featureFilesPath);
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(featureContent.toString());
            }

            logger.info("Generated feature file: {}", filePath);
            return filePath;

        } catch (Exception e) {
            logger.error("Error generating feature file from Confluence requirement", e);
            return null;
        }
    }

    /**
     * Generate test data file from Jira requirement
     */
    private String generateTestDataFile(JiraTestRequirement requirement) {
        try {
            String sanitizedTitle = sanitizeFileName(requirement.getSummary());
            String fileName = String.format("%s_data.json", sanitizedTitle);
            String filePath = Paths.get(testDataPath, fileName).toString();

            Map<String, Object> testData = new HashMap<>();
            testData.put("issueKey", requirement.getIssueKey());
            testData.put("summary", requirement.getSummary());
            testData.put("testSteps", requirement.getTestSteps());

            // Write test data file
            ensureDirectoryExists(testDataPath);
            String jsonContent = new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(testData);

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(jsonContent);
            }

            logger.info("Generated test data file: {}", filePath);
            return filePath;

        } catch (Exception e) {
            logger.error("Error generating test data file from Jira requirement", e);
            return null;
        }
    }

    /**
     * Generate test data file from Confluence requirement
     */
    private String generateTestDataFile(ConfluenceRequirement requirement) {
        try {
            String sanitizedTitle = sanitizeFileName(requirement.getTitle());
            String fileName = String.format("%s_data.json", sanitizedTitle);
            String filePath = Paths.get(testDataPath, fileName).toString();

            Map<String, Object> testData = new HashMap<>();
            testData.put("pageId", requirement.getPageId());
            testData.put("title", requirement.getTitle());
            testData.put("testData", requirement.getTestData());

            // Write test data file
            ensureDirectoryExists(testDataPath);
            String jsonContent = new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(testData);

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(jsonContent);
            }

            logger.info("Generated test data file: {}", filePath);
            return filePath;

        } catch (Exception e) {
            logger.error("Error generating test data file from Confluence requirement", e);
            return null;
        }
    }

    /**
     * Generate and run tests from both Jira and Confluence
     */
    public Map<String, Object> generateAndRunTests(String jiraJql, String confluenceCql) {
        Map<String, Object> results = new HashMap<>();

        try {
            // Generate from Jira
            List<String> jiraFiles = generateFromJira(jiraJql);
            results.put("jiraGeneratedFiles", jiraFiles);

            // Generate from Confluence
            List<String> confluenceFiles = generateFromConfluence(confluenceCql);
            results.put("confluenceGeneratedFiles", confluenceFiles);

            // Run the generated tests
            results.put("testExecution", runGeneratedTests());

            logger.info("Test generation and execution completed");
        } catch (Exception e) {
            logger.error("Error in generate and run tests", e);
            results.put("error", e.getMessage());
        }

        return results;
    }

    /**
     * Run the generated tests using Gradle
     */
    private Map<String, Object> runGeneratedTests() {
        Map<String, Object> testResults = new HashMap<>();

        try {
            // This would typically call the Gradle test task
            // For now, we'll return a placeholder
            testResults.put("status", "ready_to_run");
            testResults.put("command", ".\\gradlew.bat test");
            testResults.put("message", "Tests generated successfully. Run the command to execute.");

            logger.info("Generated tests are ready to run");
        } catch (Exception e) {
            logger.error("Error running generated tests", e);
            testResults.put("error", e.getMessage());
        }

        return testResults;
    }

    /**
     * Sanitize file name to remove invalid characters
     */
    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    /**
     * Ensure directory exists
     */
    private void ensureDirectoryExists(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}