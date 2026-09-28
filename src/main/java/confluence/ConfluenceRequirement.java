package confluence;

import java.util.List;
import java.util.Map;

/**
 * Represents a requirement or test case document from Confluence
 */
public class ConfluenceRequirement {
    private String pageId;
    private String title;
    private String content;
    private String spaceKey;
    private String spaceName;
    private String author;
    private String lastModified;
    private String version;
    private List<String> labels;
    private Map<String, Object> metadata;
    private List<String> testCases;
    private List<String> testData;
    private String requirementType; // functional, non-functional, business, etc.

    // Getters and setters
    public String getPageId() { return pageId; }
    public void setPageId(String pageId) { this.pageId = pageId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSpaceKey() { return spaceKey; }
    public void setSpaceKey(String spaceKey) { this.spaceKey = spaceKey; }
    public String getSpaceName() { return spaceName; }
    public void setSpaceName(String spaceName) { this.spaceName = spaceName; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getLastModified() { return lastModified; }
    public void setLastModified(String lastModified) { this.lastModified = lastModified; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    public List<String> getTestCases() { return testCases; }
    public void setTestCases(List<String> testCases) { this.testCases = testCases; }
    public List<String> getTestData() { return testData; }
    public void setTestData(List<String> testData) { this.testData = testData; }
    public String getRequirementType() { return requirementType; }
    public void setRequirementType(String requirementType) { this.requirementType = requirementType; }
}