# Playwright Demo Gradle

This project is a Java-based test automation suite using Gradle, Playwright, Cucumber, TestNG, Spring, and REST Assured.

## Overview

The project focuses on end-to-end validation for web and API flows, with a strong emphasis on maintainable automation patterns and AI-enabled QA extensions.

This repository is intentionally structured as a real-world QA automation foundation that can evolve toward AI-assisted validation, triage, and orchestration without replacing deterministic execution.

Key stack:
- Java 17
- Gradle
- Playwright
- Cucumber
- TestNG
- Spring
- REST Assured
- Extent Reports

## Quick Start

1. Open a PowerShell terminal in the project root.
2. Run the suite:
   ```powershell
   .\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" --rerun-tasks
   ```
3. Optional: enable strict visual regression checks:
   ```powershell
   .\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Dvisual.strict=true" --rerun-tasks
   ```

## Project Structure

- `src/main/java`
  - page objects for web and mobile flows
  - shared utilities and AI/QA helper classes
  - config and environment readers
- `src/test/java`
  - Cucumber step definitions
  - TestNG runners
  - Spring test config
  - API tests
- `src/test/resources/features`
  - Gherkin feature files
- `test-output`
  - generated reports, screenshots, baselines, triage summaries

## Run Tests

PowerShell:
```powershell
.\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" --rerun-tasks
```

Strict visual validation mode:
```powershell
.\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Dvisual.strict=true" --rerun-tasks
```

## AI / QA Additions

This project is aligned with the AI Plane e2e strategy and includes:
- resilient page object locators with fallback selectors
- visual validation with screenshot baselines
- triage summaries for failed scenarios
- project context search based on docs and feature files
- MCP-style tool registry for structured QA automation access

## AI QA Orchestrator demo

A lightweight orchestrator is available in `src/main/java/ai/AiQaOrchestrator.java`.
It combines three practical signals:
- risk-based priorities via `get_test_priorities`
- grounded project context via `ground_project_context`
- failure triage via `triage_failure`

Example usage:
```java
AiQaOrchestrator orchestrator = new AiQaOrchestrator();
Map<String, Object> plan = orchestrator.runScenarioPlan("login", "User attempts to log in", "invalid credentials error displayed");
System.out.println(plan);
```

This is intentionally small and deterministic: it does not replace the test framework, but it gives the AI a structured workflow around existing QA artifacts.

## Local run checklist

1. Install Java 17 and confirm `java -version` works.
2. Install Ollama and start the model server on `http://localhost:11434`.
3. Pull the model used by this repo, for example:
   ```bash
   ollama pull llama3.2:latest
   ```
4. Confirm the local AI endpoint is reachable and compatible with OpenAI-style chat completions.
5. From the project root, run:
   ```powershell
   .\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" --rerun-tasks
   ```
6. For strict visual validation:
   ```powershell
   .\gradlew.bat clean test "-Denv=dev" "-Dbrowser=chromium" "-Dheadless=true" "-Dvisual.strict=true" --rerun-tasks
   ```
7. Review generated artifacts under `test-output/triage`, `test-output/visual`, and `build/dev/chromium/reports`.
8. If AI is enabled, verify `ai.enabled=true` and that `ai.api.key` matches your local Ollama setup.


## Main AI QA Utilities

- `utils/VisualValidationUtils`
  - saves screenshots and compares them against baselines
- `utils/FailureTriageUtils`
  - classifies likely failure areas and causes
- `utils/QaSummaryGenerator`
  - creates a markdown QA summary for failures
- `utils/KnowledgeIndexUtils`
  - searches project docs and feature files for contextual grounding
- `utils/McpToolRegistry`
  - exposes a controlled tool registry for automation orchestration

## Important Output Folders

- `test-output/triage` — QA summaries and triage files
- `test-output/visual` — screenshots taken during test execution
- `test-output/baselines` — stored visual baselines
- `build/dev/chromium/reports` — Gradle and test reports

## Notes

This project intentionally follows a practical AI QA model:
- keep deterministic automation as the foundation
- add intelligence for diagnostics, validation, and orchestration
- keep tools auditable and bounded

## License

Project-specific license details are not defined in this repository. Use according to your team or organization’s policies.
