# Project Rules for Playwright Demo Gradle

## Project Overview
This is a Java-based test automation suite using Gradle, Playwright, Cucumber, TestNG, Spring, and REST Assured for end-to-end web and API testing.

## Build and Test Commands

### Running Tests
```powershell
.\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" --rerun-tasks
```

### Strict Visual Validation
```powershell
.\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Dvisual.strict=true" --rerun-tasks
```

### Install Playwright Browsers
```powershell
.\gradlew.bat installPlaywrightBrowsers
```

### Install Browsers with Dependencies
```powershell
.\gradlew.bat installPlaywrightBrowsersWithDeps
```

## Project Structure
- `src/main/java` - Page objects, utilities, config, AI/QA helpers
- `src/test/java` - Cucumber step definitions, TestNG runners, Spring test config, API tests
- `src/test/resources/features` - Gherkin feature files
- `test-output` - Generated reports, screenshots, baselines, triage summaries
- `build/${env}/${browser}` - Environment-specific build outputs

## Key Technologies
- Java 17
- Gradle (build system)
- Playwright (browser automation)
- Cucumber (BDD/Gherkin)
- TestNG (test framework)
- Spring (dependency injection)
- REST Assured (API testing)
- Extent Reports (reporting)
- ReportPortal (test reporting integration)

## AI/QA Components
- `src/main/java/ai/AiQaOrchestrator.java` - AI QA orchestrator for test planning and triage
- `utils/VisualValidationUtils` - Screenshot comparison against baselines
- `utils/FailureTriageUtils` - Failure classification and analysis
- `utils/QaSummaryGenerator` - Markdown QA summary generation
- `utils/KnowledgeIndexUtils` - Project context search from docs and features
- `utils/McpToolRegistry` - Tool registry for automation orchestration

## Important Directories
- `test-output/triage` - QA summaries and triage files
- `test-output/visual` - Screenshots during test execution
- `test-output/baselines` - Stored visual baselines
- `build/dev/chromium/reports` - Gradle and test reports

## Configuration
- Environment and browser are controlled via system properties: `-Denv=dev` `-Dbrowser=chromium`
- Visual validation strict mode: `-Dvisual.strict=true`
- Headless mode: `-Dheadless=true`
- ReportPortal integration is enabled by default

## Development Guidelines
- Maintain deterministic automation as the foundation
- Add intelligence for diagnostics, validation, and orchestration
- Keep tools auditable and bounded
- Use resilient page object locators with fallback selectors
- Follow the existing patterns for page objects and step definitions
- AI features should enhance, not replace, existing test framework

## Testing Approach
- Write tests in Gherkin feature files under `src/test/resources/features`
- Implement step definitions in `src/test/java`
- Use page objects for web interactions
- Follow Spring dependency injection patterns for test context
- Use TestNG for test execution and configuration
