package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestDataLoader {
    private static final Logger log = LoggerFactory.getLogger(TestDataLoader.class);
    private static final String TEST_DATA_PATH = "src/test/resources/test-data/";
    private static Map<String, Object> testDataCache;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Load test data from JSON file
     * @param fileName JSON file name (without .json extension)
     * @return Map containing test data
     */
    public static Map<String, Object> loadTestData(String fileName) {
        if (testDataCache != null) {
            return testDataCache;
        }

        try {
            File file = new File(TEST_DATA_PATH + fileName + ".json");
            testDataCache = objectMapper.readValue(file, Map.class);
            log.info("Loaded test data from: {}", file.getAbsolutePath());
            return testDataCache;
        } catch (IOException e) {
            log.error("Failed to load test data from: {}.json", fileName, e);
            throw new RuntimeException("Failed to load test data: " + fileName, e);
        }
    }

    /**
     * Get a specific value from test data using dot notation
     * @param testData The test data map
     * @param path Dot-separated path (e.g., "qapracticehub.baseUrl")
     * @return The value at the specified path
     */
    @SuppressWarnings("unchecked")
    public static String getString(Map<String, Object> testData, String path) {
        String[] keys = path.split("\\.");
        Object current = testData;

        for (String key : keys) {
            if (current instanceof Map) {
                current = ((Map<String, Object>) current).get(key);
            } else {
                return null;
            }
        }

        return current != null ? current.toString() : null;
    }

    /**
     * Get a specific nested map from test data
     * @param testData The test data map
     * @param path Dot-separated path (e.g., "qapracticehub.selectors")
     * @return The nested map at the specified path
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMap(Map<String, Object> testData, String path) {
        String[] keys = path.split("\\.");
        Object current = testData;

        for (String key : keys) {
            if (current instanceof Map) {
                current = ((Map<String, Object>) current).get(key);
            } else {
                return null;
            }
        }

        return current instanceof Map ? (Map<String, Object>) current : null;
    }

    /**
     * Clear cached test data (useful for testing)
     */
    public static void clearCache() {
        testDataCache = null;
    }
}
