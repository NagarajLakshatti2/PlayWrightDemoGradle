package utils;

import org.slf4j.MDC;

public final class TestContext {
    private TestContext() {}

    public static void setScenarioName(String name) {
        MDC.put("scenario", name);
    }

    public static void clear() {
        MDC.remove("scenario");
    }
}