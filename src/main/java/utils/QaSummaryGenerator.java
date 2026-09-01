package utils;

import config.ConfigReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QaSummaryGenerator {

    public static void generate(String scenarioName, String area, String cause, String action, String screenshotPath) throws IOException {
        generate(scenarioName, area, cause, action, screenshotPath, null);
    }

    public static void generate(String scenarioName, String area, String cause, String action, String screenshotPath, String projectContext) throws IOException {
        Path dir = Paths.get("test-output", "triage");
        Files.createDirectories(dir);

        String safeName = scenarioName.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path reportPath = dir.resolve(safeName + "-qa-summary.md");

        StringBuilder summary = new StringBuilder();
        summary.append("# QA Summary\n\n")
                .append("- Scenario: ").append(scenarioName).append("\n")
                .append("- Environment: ").append(ConfigReader.currentEnv()).append("\n")
                .append("- Browser: ").append(ConfigReader.get("browser", "chromium")).append("\n")
                .append("- Timestamp: ").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\n")
                .append("- Likely area: ").append(area).append("\n")
                .append("- Likely cause: ").append(cause).append("\n")
                .append("- Suggested action: ").append(action).append("\n")
                .append("- Screenshot: ").append(screenshotPath == null ? "Not available" : screenshotPath).append("\n");

        if (projectContext != null && !projectContext.isBlank()) {
            summary.append("\n## Relevant project context\n\n")
                    .append(projectContext)
                    .append("\n");
        }

        Files.writeString(reportPath, summary.toString());
    }
}
