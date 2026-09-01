package ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class LlmGateway {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final HttpClient httpClient;

    public LlmGateway() {
        this(HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(AiConfig.timeoutMs()))
                .build());
    }

    public LlmGateway(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String ask(String prompt) {
        if (!AiConfig.isEnabled()) {
            return "AI is disabled. Set ai.enabled=true and provide ai.api.key to enable the LLM gateway.";
        }

        String apiKey = AiConfig.apiKey();
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("AI API key is missing. Provide ai.api.key or set the AI_API_KEY environment variable.");
        }

        try {
            String requestBody = OBJECT_MAPPER.writeValueAsString(Map.of(
                    "model", AiConfig.model(),
                    "messages", List.of(
                            Map.of("role", "system", "content",
                                    "You are a senior QA automation assistant. Answer with concise, actionable, test-focused guidance."),
                            Map.of("role", "user", "content", prompt)
                    ),
                    "max_tokens", AiConfig.maxTokens()
            ));

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(AiConfig.endpoint()))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofMillis(AiConfig.timeoutMs()))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody));

            if ("azure".equals(AiConfig.provider())) {
                requestBuilder.header("api-key", apiKey);
            } else {
                requestBuilder.header("Authorization", "Bearer " + apiKey);
            }

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new IllegalStateException("LLM request failed with status " + response.statusCode() + ": " + response.body());
            }

            JsonNode root = OBJECT_MAPPER.readTree(response.body());
            JsonNode content = root.path("choices").path(0).path("message").path("content");
            if (content.isTextual()) {
                return content.asText();
            }

            return response.body();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to communicate with the configured LLM provider.", e);
        }
    }

    public String summarizeFailure(String scenarioName, String failureText) {
        String prompt = "You are a senior QA engineer. Summarize the likely root cause and next actions for this failing automation scenario. "
                + "Keep it short, structured, and actionable. Scenario: " + scenarioName + ". Failure details: " + failureText;
        return ask(prompt);
    }

    public String generateScenarioSuggestions(String requirement) {
        String prompt = "You are a test architect. Convert the following user requirement into a high-value test plan "
                + "with happy path, negative path, edge cases, and automation priorities. Requirement: " + requirement;
        return ask(prompt);
    }
}
