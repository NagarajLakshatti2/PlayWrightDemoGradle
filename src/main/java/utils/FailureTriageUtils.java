package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FailureTriageUtils {

    public static String buildSummary(String scenarioName, String failureMessage, String screenshotPath) {
        StringBuilder summary = new StringBuilder();
        summary.append("Scenario: ").append(scenarioName).append(System.lineSeparator());
        summary.append("Timestamp: ").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append(System.lineSeparator());
        summary.append("Likely area: ").append(classifyArea(failureMessage, scenarioName)).append(System.lineSeparator());
        summary.append("Likely cause: ").append(classifyCause(failureMessage, scenarioName)).append(System.lineSeparator());
        summary.append("Evidence: ").append(screenshotPath == null ? "No screenshot captured" : screenshotPath).append(System.lineSeparator());
        summary.append("Suggested action: ").append(suggestAction(classifyArea(failureMessage, scenarioName))).append(System.lineSeparator());
        return summary.toString();
    }

    public static void saveSummary(String scenarioName, String summary) throws IOException {
        Path dir = Paths.get("test-output", "triage");
        Files.createDirectories(dir);

        String safeName = scenarioName.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path path = dir.resolve(safeName + "-triage.txt");
        Files.writeString(path, summary);
    }

    public static String classifyArea(String failureMessage, String scenarioName) {
        String text = (failureMessage == null ? "" : failureMessage + " ") + scenarioName;
        String lower = text.toLowerCase();

        if (lower.contains("login") || lower.contains("credential") || lower.contains("invalid user") || lower.contains("error message")) {
            return "Login flow";
        }
        if (lower.contains("cart") || lower.contains("checkout") || lower.contains("inventory") || lower.contains("order")) {
            return "Checkout flow";
        }
        if (lower.contains("api") || lower.contains("status") || lower.contains("response") || lower.contains("restassured")) {
            return "API contract";
        }
        return "UI or environment";
    }

    public static String classifyCause(String failureMessage, String scenarioName) {
        String text = (failureMessage == null ? "" : failureMessage + " ") + scenarioName;
        String lower = text.toLowerCase();

        if (lower.contains("no qualifying bean") || lower.contains("unsatisfied dependency") || lower.contains("cucumber") || lower.contains("step definition")) {
            return "Automation wiring or step definition mismatch";
        }
        if (lower.contains("selector") || lower.contains("locator") || lower.contains("element") || lower.contains("page") || lower.contains("not visible")) {
            return "UI locator or page-state issue";
        }
        if (lower.contains("api") || lower.contains("status") || lower.contains("response") || lower.contains("500") || lower.contains("404")) {
            return "Service contract or backend response issue";
        }
        return "Likely environment drift or unexpected UI change";
    }

    public static String suggestAction(String area) {
        switch (area) {
            case "Login flow":
                return "Review login selectors and validation text; confirm auth state is correctly reached.";
            case "Checkout flow":
                return "Review cart and checkout selectors, and validate page transitions after each action.";
            case "API contract":
                return "Validate response schema and backend status codes against expected contract.";
            default:
                return "Capture a screenshot and inspect DOM/state around the failing action.";
        }
    }
}
