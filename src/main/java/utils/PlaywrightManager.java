package utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ViewportSize;
import config.ConfigReader;

public class PlaywrightManager {

    private static final ThreadLocal<Playwright> PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    public static void initBrowser() {
        Playwright playwright = Playwright.create();
        PLAYWRIGHT.set(playwright);

        String browserName = ConfigReader.get("browser", "chromium");
        boolean headless = ConfigReader.getBoolean("headless", true);
        double slowMo = ConfigReader.getInt("slow.mo", 0);

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless).setSlowMo(slowMo);

        Browser browser = switch (browserName.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            case "edge", "msedge", "microsoft-edge" -> playwright.chromium().launch(launchOptions.setChannel("msedge"));
            case "chrome", "google-chrome" -> playwright.chromium().launch(launchOptions.setChannel("chrome"));
            default -> playwright.chromium().launch(launchOptions);
        };
        BROWSER.set(browser);


        int width = ConfigReader.getInt("viewport.width", 1440);
        int height = ConfigReader.getInt("viewport.height", 900);

        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(new ViewportSize(width, height)));
        context.setDefaultTimeout(ConfigReader.getInt("default.timeout.ms", 30000));
        CONTEXT.set(context);

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true).setSnapshots(true).setSources(true));

        PAGE.set(context.newPage());
    }

    public static Page getPage() { return PAGE.get(); }
    public static BrowserContext getContext() { return CONTEXT.get(); }

    public static void stopTracing(String zipPath) {
        BrowserContext context = CONTEXT.get();
        if (context != null) {
            context.tracing().stop(new Tracing.StopOptions().setPath(java.nio.file.Paths.get(zipPath)));
        }
    }

    public static void closeBrowser() {
        if (PAGE.get() != null) { PAGE.get().close(); PAGE.remove(); }
        if (CONTEXT.get() != null) { CONTEXT.get().close(); CONTEXT.remove(); }
        if (BROWSER.get() != null) { BROWSER.get().close(); BROWSER.remove(); }
        if (PLAYWRIGHT.get() != null) { PLAYWRIGHT.get().close(); PLAYWRIGHT.remove(); }
    }
}