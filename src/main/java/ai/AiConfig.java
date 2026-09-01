package ai;

import config.ConfigReader;

public final class AiConfig {
    private AiConfig() {}

    public static boolean isEnabled() {
        return ConfigReader.getBoolean("ai.enabled", false);
    }

    public static String provider() {
        return ConfigReader.get("ai.provider", "openai").trim().toLowerCase();
    }

    public static String model() {
        return ConfigReader.get("ai.model", "gpt-4o-mini").trim();
    }

    public static String apiKey() {
        return ConfigReader.get("ai.api.key", "").trim();
    }

    public static String endpoint() {
        return ConfigReader.get("ai.endpoint", "https://api.openai.com/v1/chat/completions").trim();
    }

    public static int timeoutMs() {
        return ConfigReader.getInt("ai.timeout.ms", 30000);
    }

    public static int maxTokens() {
        return ConfigReader.getInt("ai.max.tokens", 500);
    }

    public static String authHeaderName() {
        return "azure".equals(provider()) ? "api-key" : "Authorization";
    }

    public static String authHeaderValue() {
        String apiKey = apiKey();
        if (apiKey == null || apiKey.isBlank()) {
            return "";
        }
        return "azure".equals(provider()) ? apiKey : "Bearer " + apiKey;
    }
}
