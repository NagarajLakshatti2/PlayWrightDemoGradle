package ai;

import utils.KnowledgeIndexUtils;
import utils.McpToolRegistry;
import utils.McpToolResult;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class AiQaOrchestrator {
    private final LlmGateway llmGateway;
    private final McpToolRegistry mcpToolRegistry;

    public AiQaOrchestrator() {
        this(new LlmGateway());
    }

    public AiQaOrchestrator(LlmGateway llmGateway) {
        this.llmGateway = llmGateway;
        this.mcpToolRegistry = new McpToolRegistry();
    }

    public Map<String, Object> runScenarioPlan(String changedArea, String scenarioName, String failureMessage) {
        Map<String, Object> result = new LinkedHashMap<>();

        Map<String, Object> priorityParams = new LinkedHashMap<>();
        priorityParams.put("changedArea", changedArea == null ? "overall" : changedArea);
        priorityParams.put("releaseCandidate", false);
        McpToolResult priorities = mcpToolRegistry.execute("get_test_priorities", priorityParams);
        result.put("priorities", priorities.getData());

        String searchQuery = buildSearchQuery(scenarioName, changedArea, failureMessage);
        McpToolResult groundedContext = mcpToolRegistry.execute("ground_project_context", Map.of("query", searchQuery));
        result.put("groundedContext", groundedContext.getData());

        if (failureMessage != null && !failureMessage.isBlank()) {
            Map<String, Object> triageParams = new LinkedHashMap<>();
            triageParams.put("scenario", scenarioName == null ? "unknown" : scenarioName);
            triageParams.put("failureMessage", failureMessage);
            triageParams.put("screenshotPath", "test-output/visual/" + sanitize(scenarioName));
            McpToolResult triage = mcpToolRegistry.execute("triage_failure", triageParams);
            result.put("triage", triage.getData());
        }

        return result;
    }

    public String recommendNextActions(String changedArea, String scenarioName, String failureMessage) throws IOException {
        Map<String, Object> plan = runScenarioPlan(changedArea, scenarioName, failureMessage);
        String context = KnowledgeIndexUtils.findGroundedContext(buildSearchQuery(scenarioName, changedArea, failureMessage), 1200);

        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a senior QA automation architect. Use the project context and the current execution data to recommend the next actions.\n");
        prompt.append("Scenario: ").append(scenarioName).append("\n");
        prompt.append("Changed area: ").append(changedArea == null ? "overall" : changedArea).append("\n");
        prompt.append("Failure: ").append(failureMessage == null ? "none" : failureMessage).append("\n");
        prompt.append("Execution plan: ").append(plan.get("priorities")).append("\n");
        prompt.append("Grounded context: \n").append(context).append("\n");

        return llmGateway.ask(prompt.toString());
    }

    private String buildSearchQuery(String scenarioName, String changedArea, String failureMessage) {
        StringBuilder query = new StringBuilder();
        if (scenarioName != null && !scenarioName.isBlank()) {
            query.append(scenarioName).append(" ");
        }
        if (changedArea != null && !changedArea.isBlank()) {
            query.append(changedArea).append(" ");
        }
        if (failureMessage != null && !failureMessage.isBlank()) {
            query.append(failureMessage);
        }
        String built = query.toString().trim();
        return built.isBlank() ? "login checkout QA automation" : built;
    }

    private String sanitize(String value) {
        if (value == null || value.isBlank()) {
            return "unknown";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    public static void main(String[] args) throws IOException {
        AiQaOrchestrator orchestrator = new AiQaOrchestrator();
        String scenario = "User attempts to log in";
        String changedArea = "login";
        String failureMessage = "invalid credentials error displayed";

        Map<String, Object> plan = orchestrator.runScenarioPlan(changedArea, scenario, failureMessage);
        System.out.println("AI QA PLAN: " + plan);

        System.out.println("AI RECOMMENDATION: ");
        System.out.println(orchestrator.recommendNextActions(changedArea, scenario, failureMessage));
    }
}
