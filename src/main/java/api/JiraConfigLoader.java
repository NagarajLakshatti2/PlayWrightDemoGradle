package api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Load Jira configuration from environment file
 */
public class JiraConfigLoader {

    private static final Logger log = LoggerFactory.getLogger(JiraConfigLoader.class);
    private static final String ENV_FILE_PATH = ".devin/jira-confluence.env";

    /**
     * Load Jira email and API token from environment file
     */
    public static Map<String, String> loadJiraConfig() {
        Map<String, String> config = new HashMap<>();

        try {
            File envFile = new File(ENV_FILE_PATH);
            if (!envFile.exists()) {
                log.warn("Jira environment file not found: {}", ENV_FILE_PATH);
                log.warn("Please create .devin/jira-confluence.env with JIRA_EMAIL and JIRA_TOKEN");
                return config;
            }

            List<String> lines = Files.readAllLines(Paths.get(ENV_FILE_PATH));

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    config.put(key, value);
                }
            }

            log.info("Loaded Jira configuration from {}", ENV_FILE_PATH);
            return config;

        } catch (IOException e) {
            log.error("Error loading Jira configuration", e);
            return config;
        }
    }

    /**
     * Get Jira email from environment
     */
    public static String getJiraEmail() {
        // Check environment variable first
        String email = System.getenv("JIRA_EMAIL");
        if (email != null && !email.isEmpty()) {
            return email;
        }

        // Fall back to file
        Map<String, String> config = loadJiraConfig();
        return config.get("JIRA_MCP_EMAIL");
    }

    /**
     * Get Jira API token from environment
     */
    public static String getJiraToken() {
        // Check environment variable first
        String token = System.getenv("JIRA_TOKEN");
        if (token != null && !token.isEmpty()) {
            return token;
        }

        // Fall back to file
        Map<String, String> config = loadJiraConfig();
        return config.get("JIRA_MCP_TOKEN");
    }
}
