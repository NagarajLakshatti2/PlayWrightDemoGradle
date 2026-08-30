package spring;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ViewportSize;
import config.ConfigReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;



/**
 * Declares the Playwright object graph as Spring beans, scoped
 * "cucumber-glue" — Spring + cucumber-spring create one fresh instance
 * of each bean PER SCENARIO. When scenarios run in parallel (via
 * TestNG's data-provider-thread-count), each thread gets its own
 * completely isolated Spring context and therefore its own
 * Playwright/Browser/Context/Page — this is what replaces manual
 * ThreadLocal management with container-managed isolation.
 */
@Configuration
public class SpringTestConfig {

    @Bean
    @Scope("cucumber-glue")
    public Playwright playwright() {
        return Playwright.create();
    }

    @Bean
    @Scope("cucumber-glue")
    public Browser browser(Playwright playwright) {
        String browserName = ConfigReader.get("browser", "chromium");
        boolean headless = ConfigReader.getBoolean("headless", true);
        double slowMo = ConfigReader.getInt("slow.mo", 0);

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(slowMo);

        return switch (browserName.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            case "edge", "msedge", "microsoft-edge" ->
                    playwright.chromium().launch(options.setChannel("msedge"));
            case "chrome", "google-chrome" ->
                    playwright.chromium().launch(options.setChannel("chrome"));
            default -> playwright.chromium().launch(options);
        };
    }

    @Bean
    @Scope("cucumber-glue")
    public BrowserContext browserContext(Browser browser) {
        int width = ConfigReader.getInt("viewport.width", 1440);
        int height = ConfigReader.getInt("viewport.height", 900);

        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(new ViewportSize(width, height)));
        context.setDefaultTimeout(ConfigReader.getInt("default.timeout.ms", 30000));

        context.tracing().start(new com.microsoft.playwright.Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        return context;
    }

    @Bean
    @Scope("cucumber-glue")
    public Page page(BrowserContext browserContext) {
        return browserContext.newPage();
    }
}

