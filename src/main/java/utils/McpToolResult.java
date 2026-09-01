package utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class McpToolResult {
    private final String toolName;
    private final boolean success;
    private final String message;
    private final Map<String, Object> data;

    public McpToolResult(String toolName, boolean success, String message, Map<String, Object> data) {
        this.toolName = toolName;
        this.success = success;
        this.message = message;
        this.data = data == null ? new LinkedHashMap<>() : data;
    }

    public String getToolName() {
        return toolName;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
