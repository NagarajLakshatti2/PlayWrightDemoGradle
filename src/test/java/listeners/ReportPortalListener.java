package listeners;

import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.service.Launch;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        /*
         * ReportPortal launch creation is asynchronous.
         * Start a background thread that waits for the real launch UUID.
         */
        Thread reportPortalUrlThread = new Thread(
                this::waitForLaunchUrl,
                "reportportal-launch-url"
        );

        reportPortalUrlThread.setDaemon(true);
        reportPortalUrlThread.start();
    }

    @Override
    public void onTestStart(ITestResult result) {

        /*
         * Extra safety check in case the background thread has not
         * obtained the launch UUID yet.
         */
        if (!urlPrinted) {
            printLaunchUrl();
        }
    }

    /**
     * Wait for ReportPortal to create the launch and then
     * generate the exact live launch URL.
     */
    private void waitForLaunchUrl() {

        for (int attempt = 1; attempt <= 60; attempt++) {

            if (urlPrinted) {
                return;
            }

            try {

                printLaunchUrl();

                if (urlPrinted) {
                    return;
                }

                Thread.sleep(1000);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                System.out.println(
                        "⚠ ReportPortal launch URL wait interrupted."
                );

                return;
            }
        }

        System.out.println(
                "⚠ ReportPortal launch URL was not available after 60 seconds."
        );
    }

    /**
     * Gets the actual ReportPortal launch UUID and creates
     * the exact live launch URL.
     */
    private void printLaunchUrl() {

        try {

            Launch launch = Optional.ofNullable(Launch.currentLaunch())
                    .filter(current -> current != Launch.NOOP_LAUNCH)
                    .orElse(null);

            if (launch == null) {
                return;
            }

            ListenerParameters parameters = launch.getParameters();

            String launchUuid = launch.getLaunch().blockingGet();

            if (launchUuid == null || launchUuid.isBlank()) {
                return;
            }

            String baseUrl = firstNonBlank(
                    parameters.getBaseUrl(),
                    System.getenv("RP_ENDPOINT"),
                    System.getProperty("rp.endpoint")
            );

            String projectName = firstNonBlank(
                    parameters.getProjectName(),
                    System.getenv("RP_PROJECT"),
                    System.getProperty("rp.project")
            );

            if (baseUrl == null
                    || baseUrl.isBlank()
                    || projectName == null
                    || projectName.isBlank()) {

                System.out.println(
                        "⚠ ReportPortal endpoint or project is missing."
                );

                return;
            }

            baseUrl = normalizeBaseUrl(baseUrl);

            String launchUrl =
                    baseUrl
                            + "/ui/#/"
                            + projectName
                            + "/launches/all/"
                            + launchUuid;

            String launchName = firstNonBlank(
                    parameters.getLaunchName(),
                    System.getenv("RP_LAUNCH"),
                    System.getProperty("rp.launch"),
                    "Playwright Java Tests"
            );

            /*
             * Write information for GitHub Actions.
             */
            writeGitHubLaunchFiles(
                    launchUuid,
                    launchUrl,
                    launchName
            );

            printUrl(
                    projectName,
                    launchName,
                    launchUuid,
                    launchUrl
            );

            urlPrinted = true;

        } catch (Exception e) {

            System.out.println(
                    "⚠ Unable to determine ReportPortal launch URL: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Writes ReportPortal launch information to files that
     * GitHub Actions can read while Gradle is still running.
     */
    private void writeGitHubLaunchFiles(
            String launchUuid,
            String launchUrl,
            String launchName) {

        try {

            Path buildDirectory = Paths.get("build");

            Files.createDirectories(buildDirectory);

            Files.writeString(
                    buildDirectory.resolve("reportportal-launch-uuid.txt"),
                    launchUuid,
                    StandardCharsets.UTF_8
            );

            Files.writeString(
                    buildDirectory.resolve("reportportal-launch-url.txt"),
                    launchUrl,
                    StandardCharsets.UTF_8
            );

            Files.writeString(
                    buildDirectory.resolve("reportportal-launch-name.txt"),
                    launchName,
                    StandardCharsets.UTF_8
            );

            System.out.println(
                    "✅ ReportPortal launch information written to build/"
            );

        } catch (Exception e) {

            System.out.println(
                    "⚠ Unable to write ReportPortal launch information: "
                            + e.getMessage()
            );
        }
    }

    private void printUrl(
            String projectName,
            String launchName,
            String launchUuid,
            String launchUrl) {

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

    private String normalizeBaseUrl(String baseUrl) {

        return baseUrl.endsWith("/")
                ? baseUrl.substring(0, baseUrl.length() - 1)
                : baseUrl;
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
