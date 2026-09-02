package listeners;

import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.service.Launch;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Optional;

public class ReportPortalListener implements IExecutionListener, ITestListener {

    private static final String PREFIX =
            "\n============================================================";

    private volatile boolean urlPrinted = false;

    @Override
    public void onExecutionStart() {
        System.out.println(PREFIX);
        System.out.println("🚀 REPORT PORTAL EXECUTION STARTED");
        System.out.println(PREFIX);
        printLaunchUrl();
    }

    @Override
    public void onTestStart(ITestResult result) {
        if (!urlPrinted) {
            printLaunchUrl();
        }
    }

    private void printLaunchUrl() {

        try {
            Launch launch = Optional.ofNullable(Launch.currentLaunch())
                    .filter(current -> current != Launch.NOOP_LAUNCH)
                    .orElse(null);

            if (launch != null) {
                ListenerParameters parameters = launch.getParameters();
                String launchUuid = launch.getLaunch().blockingGet();

                if (launchUuid != null && !launchUuid.isBlank()) {
                    String baseUrl = firstNonBlank(
                            parameters.getBaseUrl(),
                            System.getenv("RP_ENDPOINT"),
                            System.getProperty("rp.endpoint")
                    );

                    if (baseUrl != null && !baseUrl.isBlank()) {
                        String projectName = firstNonBlank(
                                parameters.getProjectName(),
                                System.getenv("RP_PROJECT"),
                                System.getProperty("rp.project")
                        );

                        if (projectName != null && !projectName.isBlank()) {
                            baseUrl = baseUrl.endsWith("/")
                                    ? baseUrl.substring(0, baseUrl.length() - 1)
                                    : baseUrl;

                            String launchUrl =
                                    baseUrl
                                            + "/ui/#/"
                                            + projectName
                                            + "/launches/all/"
                                            + launchUuid;

                            printUrl(projectName, parameters.getLaunchName(), launchUuid, launchUrl);
                            urlPrinted = true;
                            return;
                        }
                    }
                }
            }

            String fallbackUrl = buildFallbackUrl();
            if (fallbackUrl == null) {
                return;
            }

            String projectName = firstNonBlank(
                    System.getenv("RP_PROJECT"),
                    System.getProperty("rp.project")
            );

            String launchName = firstNonBlank(
                    System.getenv("RP_LAUNCH"),
                    System.getProperty("rp.launch"),
                    "Playwright Demo Gradle - Suite"
            );

            printUrl(projectName, launchName, "n/a", fallbackUrl);
            urlPrinted = true;

        } catch (Exception e) {
            System.out.println(
                    "⚠ Unable to determine ReportPortal launch URL: "
                            + e.getMessage()
            );
        }
    }

    private String buildFallbackUrl() {
        String baseUrl = firstNonBlank(
                System.getenv("RP_ENDPOINT"),
                System.getProperty("rp.endpoint")
        );

        String projectName = firstNonBlank(
                System.getenv("RP_PROJECT"),
                System.getProperty("rp.project")
        );

        if (baseUrl == null || projectName == null) {
            return null;
        }

        String launchName = firstNonBlank(
                System.getenv("RP_LAUNCH"),
                System.getProperty("rp.launch")
        );

        if (launchName == null) {
            launchName = "Playwright Demo Gradle - Suite";
        }

        String normalizedBaseUrl = baseUrl.endsWith("/")
                ? baseUrl.substring(0, baseUrl.length() - 1)
                : baseUrl;

        return normalizedBaseUrl
                + "/ui/#"
                + projectName
                + "/launches/all?filter.eq.name="
                + java.net.URLEncoder.encode(launchName, java.nio.charset.StandardCharsets.UTF_8);
    }

    private void printUrl(String projectName, String launchName, String launchUuid, String launchUrl) {
        System.out.println(PREFIX);
        System.out.println("🔴 LIVE REPORT PORTAL LAUNCH");
        System.out.println(PREFIX);
        System.out.println("Project : " + projectName);
        System.out.println("Launch  : " + launchName);
        System.out.println("UUID    : " + launchUuid);
        System.out.println();
        System.out.println("🔗 LIVE REPORT:");
        System.out.println(launchUrl);
        System.out.println(PREFIX);
        System.out.println();
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
        }
        return null;
    }

    @Override
    public void onExecutionFinish() {
        System.out.println(PREFIX);
        System.out.println("🏁 REPORT PORTAL EXECUTION FINISHED");
        System.out.println(PREFIX);
    }
}