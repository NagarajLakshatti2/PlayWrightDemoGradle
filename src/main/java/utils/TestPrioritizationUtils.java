package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TestPrioritizationUtils {

    private TestPrioritizationUtils() {
    }

    public static Map<String, Object> buildExecutionPlan(String changedArea, boolean releaseCandidate) {
        List<String> features = List.of(
                "src/test/resources/features/login.feature",
                "src/test/resources/features/web/checkout.feature",
                "src/test/resources/features/mobile/login.feature",
                "src/test/resources/features/mobile/onboarding.feature",
                "src/test/resources/features/web/login.feature"
        );

        Map<String, Integer> scores = new LinkedHashMap<>();
        for (String feature : features) {
            scores.put(feature, scoreFeature(feature, changedArea));
        }

        List<Map<String, Object>> ranked = new ArrayList<>();
        scores.entrySet().stream()
                .sorted((left, right) -> Integer.compare(right.getValue(), left.getValue()))
                .forEach(entry -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("feature", entry.getKey());
                    item.put("score", entry.getValue());
                    item.put("priority", classifyPriority(entry.getValue()));
                    ranked.add(item);
                });

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("changedArea", changedArea == null ? "unknown" : changedArea);
        result.put("releaseCandidate", releaseCandidate);
        result.put("smoke", buildSmokeSet(ranked, releaseCandidate));
        result.put("regression", buildRegressionSet(ranked, releaseCandidate));
        result.put("rankedFeatures", ranked);
        return result;
    }

    public static List<String> buildSmokeSet(List<Map<String, Object>> ranked, boolean releaseCandidate) {
        if (ranked == null || ranked.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> smoke = new ArrayList<>();
        for (Map<String, Object> item : ranked) {
            String feature = String.valueOf(item.get("feature"));
            int score = Integer.parseInt(String.valueOf(item.get("score")));
            if (score >= 80 || releaseCandidate) {
                smoke.add(feature);
            }
            if (smoke.size() >= 3) {
                break;
            }
        }
        return smoke;
    }

    public static List<String> buildRegressionSet(List<Map<String, Object>> ranked, boolean releaseCandidate) {
        if (ranked == null || ranked.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> regression = new ArrayList<>();
        for (Map<String, Object> item : ranked) {
            regression.add(String.valueOf(item.get("feature")));
            if (regression.size() >= (releaseCandidate ? ranked.size() : 4)) {
                break;
            }
        }
        return regression;
    }

    private static String classifyPriority(int score) {
        if (score >= 90) {
            return "P0";
        }
        if (score >= 75) {
            return "P1";
        }
        if (score >= 50) {
            return "P2";
        }
        return "P3";
    }

    private static int scoreFeature(String feature, String changedArea) {
        String normalizedFeature = feature.toLowerCase(Locale.ROOT);
        String normalizedArea = changedArea == null ? "" : changedArea.toLowerCase(Locale.ROOT);

        int score = 30;

        if (normalizedFeature.contains("login")) {
            score += 45;
        }
        if (normalizedFeature.contains("checkout")) {
            score += 50;
        }
        if (normalizedFeature.contains("onboarding")) {
            score += 15;
        }
        if (normalizedFeature.contains("web")) {
            score += 10;
        }

        if (!normalizedArea.isBlank()) {
            if (normalizedArea.contains("login") && normalizedFeature.contains("login")) {
                score += 30;
            }
            if (normalizedArea.contains("checkout") && normalizedFeature.contains("checkout")) {
                score += 30;
            }
            if (normalizedArea.contains("mobile") && normalizedFeature.contains("mobile")) {
                score += 25;
            }
            if (normalizedArea.contains("auth") && normalizedFeature.contains("login")) {
                score += 20;
            }
        }

        return Math.min(score, 100);
    }
}
