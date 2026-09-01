package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class McpToolRegistry {

    private final Map<String, java.util.function.Supplier<McpToolResult>> tools = new LinkedHashMap<>();

    public McpToolRegistry() {
        register("run_login_flow", () -> {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("scenario", "src/test/resources/features/login.feature");
            return new McpToolResult("run_login_flow", true, "Login flow definition is available", data);
        });

        register("run_checkout_flow", () -> {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("scenario", "src/test/resources/features/web/checkout.feature");
            return new McpToolResult("run_checkout_flow", true, "Checkout flow definition is available", data);
        });

        register("search_project_knowledge", () -> {
            try {
                List<String> docs = KnowledgeIndexUtils.collectGroundedKnowledge();
                Map<String, Object> data = new LinkedHashMap<>();
                data.put("documentCount", docs.size());
                data.put("documents", docs);
                return new McpToolResult("search_project_knowledge", true, "Grounded knowledge documents indexed", data);
            } catch (IOException e) {
                return new McpToolResult("search_project_knowledge", false, e.getMessage(), null);
            }
        });

        register("ground_project_context", () -> {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("purpose", "Return grounded project context from docs, features, and QA summaries.");
            return new McpToolResult("ground_project_context", true, "Grounded context tool available", data);
        });

        register("get_qa_summary", () -> {
            try {
                Path triageDir = Paths.get("test-output", "triage");
                List<String> files = new ArrayList<>();
                if (Files.exists(triageDir)) {
                    Files.list(triageDir)
                            .filter(path -> path.toString().endsWith(".md") || path.toString().endsWith(".txt"))
                            .forEach(path -> files.add(path.toString()));
                }
                Map<String, Object> data = new LinkedHashMap<>();
                data.put("files", files);
                return new McpToolResult("get_qa_summary", true, "QA summary list retrieved", data);
            } catch (IOException e) {
                return new McpToolResult("get_qa_summary", false, e.getMessage(), null);
            }
        });

        register("get_visual_baseline", () -> {
            try {
                Path baselineRoot = Paths.get("test-output", "baselines");
                List<String> baselines = new ArrayList<>();
                if (Files.exists(baselineRoot)) {
                    Files.walk(baselineRoot)
                            .filter(path -> path.toString().endsWith(".png"))
                            .forEach(path -> baselines.add(path.toString()));
                }
                Map<String, Object> data = new LinkedHashMap<>();
                data.put("baselines", baselines);
                return new McpToolResult("get_visual_baseline", true, "Baseline images retrieved", data);
            } catch (IOException e) {
                return new McpToolResult("get_visual_baseline", false, e.getMessage(), null);
            }
        });

        register("get_test_priorities", () -> {
            Map<String, Object> data = TestPrioritizationUtils.buildExecutionPlan("overall", false);
            return new McpToolResult("get_test_priorities", true, "Risk-based test plan generated", data);
        });

        register("triage_failure", () -> {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("area", "Login flow");
            data.put("cause", "Likely environment drift or unexpected UI change");
            data.put("suggestedAction", "Review login selectors and validation text; confirm auth state is correctly reached.");
            return new McpToolResult("triage_failure", true, "Failure triage template generated", data);
        });

        register("analyze_visual_drift", () -> {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("scenario", "unknown");
            data.put("phase", "default");
            data.put("status", "missing-context");
            data.put("summary", "No visual validation context was supplied. Use the scenario and phase parameters.");
            return new McpToolResult("analyze_visual_drift", true, "Visual analysis template generated", data);
        });
    }

    public void register(String toolName, java.util.function.Supplier<McpToolResult> action) {
        tools.put(toolName, action);
    }

    public Map<String, java.util.function.Supplier<McpToolResult>> getTools() {
        return tools;
    }

    public McpToolResult execute(String toolName) {
        return execute(toolName, Map.of());
    }

    public McpToolResult execute(String toolName, Map<String, Object> parameters) {
        if ("search_project_knowledge".equals(toolName)) {
            String query = parameters == null ? "" : String.valueOf(parameters.getOrDefault("query", "")).trim();
            try {
                List<String> matches = KnowledgeIndexUtils.searchKnowledge(query);
                Map<String, Object> data = new LinkedHashMap<>();
                data.put("query", query);
                data.put("matches", matches);
                return new McpToolResult(toolName, true, "Relevant grounded project knowledge returned", data);
            } catch (IOException e) {
                return new McpToolResult(toolName, false, e.getMessage(), null);
            }
        }

        if ("ground_project_context".equals(toolName)) {
            String query = parameters == null ? "" : String.valueOf(parameters.getOrDefault("query", "")).trim();
            try {
                String context = KnowledgeIndexUtils.findGroundedContext(query, 1200);
                Map<String, Object> data = new LinkedHashMap<>();
                data.put("query", query);
                data.put("context", context);
                return new McpToolResult(toolName, true, "Grounded project context assembled", data);
            } catch (IOException e) {
                return new McpToolResult(toolName, false, e.getMessage(), null);
            }
        }

        if ("get_test_priorities".equals(toolName)) {
            String changedArea = parameters == null ? "" : String.valueOf(parameters.getOrDefault("changedArea", "overall"));
            boolean releaseCandidate = parameters != null && Boolean.parseBoolean(String.valueOf(parameters.getOrDefault("releaseCandidate", false)));
            Map<String, Object> data = TestPrioritizationUtils.buildExecutionPlan(changedArea, releaseCandidate);
            return new McpToolResult(toolName, true, "Risk-based test plan generated", data);
        }

        if ("triage_failure".equals(toolName)) {
            String scenario = parameters == null ? "unknown" : String.valueOf(parameters.getOrDefault("scenario", "unknown"));
            String failureMessage = parameters == null ? "" : String.valueOf(parameters.getOrDefault("failureMessage", ""));
            String screenshotPath = parameters == null ? "" : String.valueOf(parameters.getOrDefault("screenshotPath", ""));
            String area = FailureTriageUtils.classifyArea(failureMessage, scenario);
            String cause = FailureTriageUtils.classifyCause(failureMessage, scenario);
            String action = FailureTriageUtils.suggestAction(area);
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("scenario", scenario);
            data.put("area", area);
            data.put("cause", cause);
            data.put("suggestedAction", action);
            data.put("summary", FailureTriageUtils.buildSummary(scenario, failureMessage, screenshotPath));
            return new McpToolResult(toolName, true, "Failure triage generated", data);
        }

        if ("analyze_visual_drift".equals(toolName)) {
            String scenario = parameters == null ? "unknown" : String.valueOf(parameters.getOrDefault("scenario", "unknown"));
            String phase = parameters == null ? "default" : String.valueOf(parameters.getOrDefault("phase", "default"));
            double threshold = parameters == null ? 2.0 : Double.parseDouble(String.valueOf(parameters.getOrDefault("threshold", 2.0)));
            try {
                Map<String, Object> data = VisualValidationUtils.analyzeVisualDrift(scenario, phase, threshold);
                return new McpToolResult(toolName, true, "Visual drift analysis completed", data);
            } catch (IOException e) {
                return new McpToolResult(toolName, false, e.getMessage(), null);
            }
        }

        java.util.function.Supplier<McpToolResult> tool = tools.get(toolName);
        if (tool == null) {
            return new McpToolResult(toolName, false, "Unknown MCP tool: " + toolName, null);
        }
        return tool.get();
    }
}
