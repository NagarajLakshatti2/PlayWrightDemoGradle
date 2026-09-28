package jira;

import java.util.List;
import java.util.Map;

/**
 * Represents a test requirement or test case from Jira
 */
public class JiraTestRequirement {
    private String issueKey;
    private String summary;
    private String description;
    private String status;
    private String priority;
    private String assignee;
    private List<String> labels;
    private Map<String, Object> customFields;
    private String testCaseType; // functional, integration, e2e, etc.
    private List<String> acceptanceCriteria;
    private List<TestStep> testSteps;

    public static class TestStep {
        private int stepNumber;
        private String action;
        private String expectedResult;
        private String testData;

        public TestStep(int stepNumber, String action, String expectedResult, String testData) {
            this.stepNumber = stepNumber;
            this.action = action;
            this.expectedResult = expectedResult;
            this.testData = testData;
        }

        // Getters and setters
        public int getStepNumber() { return stepNumber; }
        public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        public String getExpectedResult() { return expectedResult; }
        public void setExpectedResult(String expectedResult) { this.expectedResult = expectedResult; }
        public String getTestData() { return testData; }
        public void setTestData(String testData) { this.testData = testData; }
    }

    // Getters and setters
    public String getIssueKey() { return issueKey; }
    public void setIssueKey(String issueKey) { this.issueKey = issueKey; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getAssignee() { return assignee; }
    public void setAssignee(String assignee) { this.assignee = assignee; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public Map<String, Object> getCustomFields() { return customFields; }
    public void setCustomFields(Map<String, Object> customFields) { this.customFields = customFields; }
    public String getTestCaseType() { return testCaseType; }
    public void setTestCaseType(String testCaseType) { this.testCaseType = testCaseType; }
    public List<String> getAcceptanceCriteria() { return acceptanceCriteria; }
    public void setAcceptanceCriteria(List<String> acceptanceCriteria) { this.acceptanceCriteria = acceptanceCriteria; }
    public List<TestStep> getTestSteps() { return testSteps; }
    public void setTestSteps(List<TestStep> testSteps) { this.testSteps = testSteps; }
}