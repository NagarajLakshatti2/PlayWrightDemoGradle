package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties PROPERTIES = new Properties();
    private static volatile boolean loaded = false;

    private ConfigReader() {}

    private static void loadIfNeeded() {
        if (loaded) return;
        synchronized (ConfigReader.class) {
            if (loaded) {
                return;
            }

            String env = resolveEnv();
            loadResourceInto(PROPERTIES, "config/common.properties", true);
            loadResourceInto(PROPERTIES, "config/" + env + ".properties", false);
            loadLocalPropertiesIfPresent();

            loaded = true;
        }
    }

    private static void loadLocalPropertiesIfPresent() {
        java.nio.file.Path localProps = java.nio.file.Paths.get("ai.local.properties");
        if (!java.nio.file.Files.exists(localProps)) {
            return;
        }

        try (InputStream is = java.nio.file.Files.newInputStream(localProps)) {
            PROPERTIES.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ai.local.properties", e);
        }
    }

    private static String resolveEnv() {
        String env = System.getProperty("env");
        if (env == null || env.isBlank()) {
            env = System.getenv("ENV");
        }
        return (env == null || env.isBlank()) ? "dev" : env.toLowerCase();
    }

    private static void loadResourceInto(Properties target, String resourcePath, boolean required) {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                if (required) {
                    throw new RuntimeException(resourcePath + " not found on classpath");
                }
                System.err.println("WARN: " + resourcePath + " not found — skipping (is 'env' spelled correctly?)");
                return;
            }
            target.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + resourcePath, e);
        }
    }

    public static String get(String key) {
        loadIfNeeded();
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp;
        }

        String envKey = key.toUpperCase().replace('.', '_');
        String envVal = System.getenv(envKey);
//        String envVal = System.getenv(key.toUpperCase().replace('.', '_'));
        if (envVal != null && !envVal.isBlank()) {
            return envVal;
        }
        return PROPERTIES.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public static String currentEnv() {
        loadIfNeeded();
        return resolveEnv();
    }

    public static String baseUrl() {
        return get("base.url");
    }

    public static String apiBaseUrl() {
        return get("api.base.url");
    }
}
