package stepdefinitions.hooks;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class ReportPortalLinkHelper {

    private static boolean launchStarted;

    private ReportPortalLinkHelper() {
    }

    public static synchronized void startLaunch() {
        if (launchStarted) {
            return;
        }

        String url = buildLaunchUrl(null);
        if (url == null) {
            return;
        }

        String launchName = resolveLaunchName(null);
        System.out.println("ReportPortal suite launch started: " + launchName);
        System.out.println("ReportPortal suite URL: " + url);
        System.out.println("Open ReportPortal suite execution: " + url);
        launchStarted = true;
    }

    public static synchronized void stopLaunch() {
        if (!launchStarted) {
            return;
        }

        String url = buildLaunchUrl(null);
        if (url == null) {
            launchStarted = false;
            return;
        }

        System.out.println("ReportPortal suite execution finished.");
        System.out.println("ReportPortal suite link: " + url);
        launchStarted = false;
    }

    public static void logBeforeScenario(String scenarioName) {
        String url = buildLaunchUrl(scenarioName);
        if (url == null) {
            return;
        }

        String launchName = resolveLaunchName(scenarioName);
        System.out.println("ReportPortal launch: " + launchName);
        System.out.println("ReportPortal URL: " + url);
        System.out.println("Open ReportPortal execution: " + url);
    }

    public static void logAfterScenario(String scenarioName, String status) {
        String url = buildLaunchUrl(scenarioName);
        if (url == null) {
            return;
        }

        System.out.println("ReportPortal status: " + status);
        System.out.println("ReportPortal execution link: " + url);
    }

    private static String buildLaunchUrl(String scenarioName) {
        String endpoint = firstNonBlank(
                System.getenv("RP_ENDPOINT"),
                System.getProperty("rp.endpoint"),
                readProperty("rp.endpoint")
        );
        String project = firstNonBlank(
                System.getenv("RP_PROJECT"),
                System.getProperty("rp.project"),
                readProperty("rp.project")
        );
        String apiKey = firstNonBlank(
                System.getenv("RP_API_KEY"),
                System.getProperty("rp.api.key"),
                readProperty("rp.api.key")
        );

        if (isBlank(endpoint) || isBlank(project) || isBlank(apiKey)) {
            return null;
        }

        String launchName = resolveLaunchName(scenarioName);
        String encodedLaunch = URLEncoder.encode(launchName, StandardCharsets.UTF_8);
        String normalizedEndpoint = endpoint.trim();
        if (!normalizedEndpoint.endsWith("/")) {
            normalizedEndpoint = normalizedEndpoint + "/";
        }

        return normalizedEndpoint + "ui/#" + project.trim() + "/launches/all?filter.eq.name=" + encodedLaunch;
    }

    private static String resolveLaunchName(String scenarioName) {
        String launchName = firstNonBlank(
                System.getenv("RP_LAUNCH"),
                System.getProperty("rp.launch"),
                readProperty("rp.launch")
        );
        if (isBlank(launchName)) {
            if (scenarioName == null || scenarioName.trim().isEmpty()) {
                launchName = "Playwright Demo Gradle - Suite";
            } else {
                launchName = "Playwright Demo Gradle - " + scenarioName;
            }
        }
        return launchName.trim();
    }

    private static String readProperty(String key) {
        Properties props = new Properties();
        try (var input = ReportPortalLinkHelper.class.getClassLoader()
                .getResourceAsStream("reportportal.properties")) {
            if (input == null) {
                return null;
            }
            props.load(input);
            return props.getProperty(key);
        } catch (Exception e) {
            return null;
        }
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
        }
        return null;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
