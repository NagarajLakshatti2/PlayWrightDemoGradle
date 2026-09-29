# Jira/Confluence Integration Setup Guide

This guide explains how to configure and use the Jira/Confluence integration for automated test case generation and execution.

## Overview

The integration allows you to:
- Read requirements and test cases from Jira issues
- Read requirements and test data from Confluence pages
- Automatically generate Cucumber feature files from requirements
- Execute generated tests locally and in GitHub Actions
- Update test execution status back to Jira
- Publish test reports to Confluence

## Prerequisites

### 1. Jira Setup
- Jira Cloud or Data Center instance
- API token from [Atlassian Account Settings](https://id.atlassian.com/manage-profile/security/api-tokens)
- Appropriate permissions to read issues and update test execution status

### 2. Confluence Setup
- Confluence Cloud or Data Center instance
- API token (same as Jira for Cloud instances)
- Appropriate permissions to read pages and create test reports

### 3. Required MCP Servers
- `atlassian-jira-mcp` (Python package)
- `atlassian-confluence-mcp-server` (npm package)

## Configuration

### 1. Set Up Environment Variables

Copy the example environment file and add your credentials:

```bash
cp .devin/jira-confluence.env.example .devin/jira-confluence.env
```

Edit `.devin/jira-confluence.env` with your actual credentials:

```env
# Jira Configuration
JIRA_MCP_URL=https://yourcompany.atlassian.net
JIRA_MCP_EMAIL=your.email@company.com
JIRA_MCP_TOKEN=your-api-token-here

# Confluence Configuration
CONFLUENCE_URL=https://yourcompany.atlassian.net/wiki
CONFLUENCE_EMAIL=your.email@company.com
CONFLUENCE_TOKEN=your-api-token-here
```

### 2. Configure GitHub Secrets

For GitHub Actions integration, add the following secrets to your repository:

1. Go to your repository Settings → Secrets and variables → Actions
2. Add the following secrets:

| Secret Name | Description | Example |
|-------------|-------------|---------|
| `JIRA_URL` | Jira instance URL | `https://yourcompany.atlassian.net` |
| `JIRA_EMAIL` | Jira account email | `your.email@company.com` |
| `JIRA_TOKEN` | Jira API token | `your-api-token-here` |
| `CONFLUENCE_URL` | Confluence instance URL | `https://yourcompany.atlassian.net/wiki` |
| `CONFLUENCE_EMAIL` | Confluence account email | `your.email@company.com` |
| `CONFLUENCE_TOKEN` | Confluence API token | `your-api-token-here` |

### 3. Install MCP Servers

#### For Local Development (Devin Desktop)

The MCP servers are configured in `.devin/mcp_config.json`. You need to install them:

```bash
# Install Jira MCP server (Python)
pip install atlassian-jira-mcp

# Install Confluence MCP server (npm - already configured via npx)
# No installation needed, it will be auto-downloaded
```

#### For GitHub Actions

The GitHub Actions workflow automatically installs the required MCP servers.

## Usage

### Local Development

#### 1. Test MCP Server Connection

```java
import jira.JiraIntegrationUtil;
import confluence.ConfluenceIntegrationUtil;

public class TestMcpConnection {
    public static void main(String[] args) {
        // Test Jira connection
        JiraIntegrationUtil jira = new JiraIntegrationUtil();
        var requirements = jira.searchTestRequirements("project = TEST");
        System.out.println("Found " + requirements.size() + " Jira requirements");

        // Test Confluence connection
        ConfluenceIntegrationUtil confluence = new ConfluenceIntegrationUtil();
        var confluenceReqs = confluence.searchRequirements("label = \"test-requirement\"");
        System.out.println("Found " + confluenceReqs.size() + " Confluence requirements");
    }
}
```

#### 2. Generate Test Cases from Jira

```java
import integration.TestCaseGenerator;

public class GenerateFromJira {
    public static void main(String[] args) {
        TestCaseGenerator generator = new TestCaseGenerator(
            "src/test/resources/features/jira",
            "src/test/resources/test-data/jira"
        );

        String jql = "project = TEST AND issuetype = Test ORDER BY created DESC";
        List<String> generatedFiles = generator.generateFromJira(jql);

        System.out.println("Generated files: " + generatedFiles);
    }
}
```

#### 3. Generate Test Cases from Confluence

```java
import integration.TestCaseGenerator;

public class GenerateFromConfluence {
    public static void main(String[] args) {
        TestCaseGenerator generator = new TestCaseGenerator(
            "src/test/resources/features/confluence",
            "src/test/resources/test-data/confluence"
        );

        String cql = "label = \"test-requirement\" ORDER BY created DESC";
        List<String> generatedFiles = generator.generateFromConfluence(cql);

        System.out.println("Generated files: " + generatedFiles);
    }
}
```

#### 4. Run Generated Tests

```bash
# Run tests generated from Jira
.\gradlew.bat test -Denv=dev -Dbrowser=chromium -Dheadless=true

# Run tests generated from Confluence
.\gradlew.bat test -Denv=dev -Dbrowser=chromium -Dheadless=true
```

### GitHub Actions Integration

#### 1. Manual Workflow Trigger

Go to Actions → Jira/Confluence Test Integration → Run workflow

Configure the parameters:
- **Jira JQL**: JQL query to fetch test requirements (e.g., `project = TEST AND issuetype = Test`)
- **Confluence CQL**: CQL query to fetch requirements (e.g., `label = "test-requirement"`)
- **Environment**: Target environment (dev/staging/prod)
- **Browser**: Target browser (chromium/firefox/webkit/edge)
- **Update Jira**: Whether to update test execution status in Jira
- **Update Confluence**: Whether to publish test report to Confluence

#### 2. Automated Workflow

You can also trigger the workflow automatically on push/PR by modifying the workflow trigger conditions.

## Integration Components

### Jira Integration Utility (`JiraIntegrationUtil.java`)

Key methods:
- `searchTestRequirements(String jqlQuery)` - Search for test requirements using JQL
- `getTestRequirement(String issueKey)` - Get a specific requirement by issue key
- `getTestCasesFromProject(String projectKey)` - Get all test cases from a project
- `updateTestExecution(String issueKey, String status, String comment)` - Update test execution status
- `createTestCase(String projectKey, String summary, String description, List<String> labels)` - Create new test case

### Confluence Integration Utility (`ConfluenceIntegrationUtil.java`)

Key methods:
- `searchRequirements(String cqlQuery)` - Search for requirements using CQL
- `getRequirement(String pageId)` - Get a specific requirement by page ID
- `getPagesFromSpace(String spaceKey)` - Get all pages from a space
- `createTestReport(String spaceKey, String title, String content, List<String> labels)` - Create test report
- `updatePage(String pageId, String content)` - Update existing page
- `addComment(String pageId, String comment)` - Add comment to page

### Test Case Generator (`TestCaseGenerator.java`)

Key methods:
- `generateFromJira(String jqlQuery)` - Generate feature files from Jira requirements
- `generateFromConfluence(String cqlQuery)` - Generate feature files from Confluence requirements
- `generateAndRunTests(String jiraJql, String confluenceCql)` - Generate and run tests

## Directory Structure

After running the integration, the following structure will be created:

```
src/test/resources/
├── features/
│   ├── jira/
│   │   ├── LoginTest.feature
│   │   ├── CheckoutTest.feature
│   │   └── ...
│   └── confluence/
│       ├── Requirement123.feature
│       ├── Requirement456.feature
│       └── ...
└── test-data/
    ├── jira/
    │   ├── LoginTest_data.json
    │   ├── CheckoutTest_data.json
    │   └── ...
    └── confluence/
        ├── Requirement123_data.json
        ├── Requirement456_data.json
        └── ...
```

## Best Practices

### 1. Jira Issue Structure

Ensure your Jira test cases follow this structure:
- **Summary**: Clear, descriptive test case name
- **Description**: Detailed test description with acceptance criteria
- **Labels**: Use labels like `functional`, `integration`, `e2e`, `smoke`
- **Custom Fields**: Use custom fields for test steps if available
- **Priority**: Set appropriate priority levels

### 2. Confluence Page Structure

Organize Confluence requirements as follows:
- **Title**: Clear requirement name
- **Labels**: Use labels like `test-requirement`, `functional`, `api`, `ui`
- **Content**: Include test cases in tables or structured lists
- **Test Data**: Store test data in code blocks or data tables
- **Space**: Organize by project or feature area

### 3. Query Optimization

Use efficient JQL and CQL queries:
- **JQL**: `project = TEST AND issuetype = Test AND status = "Ready for Testing"`
- **CQL**: `space = TEST AND label = "test-requirement" AND lastModified > -7d`

### 4. Error Handling

The integration utilities include comprehensive error handling:
- Network failures are logged and return empty collections
- Invalid credentials are caught and reported
- Malformed responses are handled gracefully

## Troubleshooting

### MCP Server Connection Issues

1. **Check credentials**: Verify your API tokens are valid
2. **Test connectivity**: Use the test connection methods
3. **Check logs**: Review MCP server logs for detailed errors

### Feature File Generation Issues

1. **Check permissions**: Ensure you have read access to Jira/Confluence
2. **Validate queries**: Test your JQL/CQL queries in the web interface
3. **Check directory permissions**: Ensure the generator can write to feature directories

### GitHub Actions Issues

1. **Verify secrets**: Check that all required secrets are configured
2. **Check workflow logs**: Review the GitHub Actions logs for errors
3. **Test locally**: Run the integration locally before pushing to GitHub

## Security Considerations

1. **Never commit credentials**: Always use environment variables or secrets
2. **Use read-only mode**: Set `JIRA_READ_ONLY=true` and `CONFLUENCE_READ_ONLY=true` for safety
3. **Limit permissions**: Use API tokens with minimal required permissions
4. **Rotate tokens**: Regularly rotate your API tokens

## Advanced Features

### Custom Test Step Parsing

The integration can be extended to parse custom test step formats from Jira custom fields.

### Bi-directional Sync

Enable full bi-directional sync by:
1. Reading requirements from Jira/Confluence
2. Generating and executing tests
3. Updating test execution status back to Jira
4. Publishing detailed reports to Confluence

### AI-Powered Test Generation

Combine with the existing AI integration to:
- Analyze requirements and suggest test cases
- Generate step definitions automatically
- Prioritize tests based on risk analysis

## Support

For issues or questions:
1. Check the MCP server documentation
2. Review the integration utilities source code
3. Check GitHub Actions logs
4. Consult the project AGENTS.md file