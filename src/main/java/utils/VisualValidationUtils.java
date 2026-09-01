package utils;

import com.microsoft.playwright.Page;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class VisualValidationUtils {

    private VisualValidationUtils() {
    }

    public static Path saveScreenshot(Page page, String scenarioName, String phase) throws IOException {
        byte[] screenshot = page.screenshot();

        Path dir = Paths.get("test-output", "visual", scenarioName, phase);
        Files.createDirectories(dir);

        Path path = dir.resolve("screenshot.png");
        Files.write(path, screenshot);
        return path;
    }

    public static Map<String, Object> analyzeVisualDrift(String scenarioName, String phase, double maxDiffPercent) throws IOException {
        Path actualPath = Paths.get("test-output", "visual", scenarioName, phase, "screenshot.png");
        Path baselinePath = Paths.get("test-output", "baselines", scenarioName, phase, "screenshot.png");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("scenario", scenarioName);
        result.put("phase", phase);
        result.put("threshold", maxDiffPercent);
        result.put("actualPath", actualPath.toString());
        result.put("baselinePath", baselinePath.toString());

        if (!Files.exists(actualPath)) {
            result.put("status", "missing-actual");
            result.put("summary", "No visual screenshot exists for " + scenarioName + " / " + phase + ".");
            return result;
        }

        if (!Files.exists(baselinePath)) {
            Files.createDirectories(baselinePath.getParent());
            Files.copy(actualPath, baselinePath, StandardCopyOption.REPLACE_EXISTING);
            result.put("status", "baseline-created");
            result.put("summary", "Baseline created for " + scenarioName + " / " + phase + ".");
            return result;
        }

        double diffPercent = compareImages(actualPath, baselinePath);
        boolean driftDetected = diffPercent > maxDiffPercent;
        result.put("diffPercent", diffPercent);
        result.put("status", driftDetected ? "drift-detected" : "passed");
        result.put("summary", buildVisualSummary(scenarioName, phase, diffPercent, maxDiffPercent));
        return result;
    }

    public static String buildVisualSummary(String scenarioName, String phase, double diffPercent, double threshold) {
        if (diffPercent > threshold) {
            return "Visual regression detected for " + scenarioName + " / " + phase + ": "
                    + diffPercent + "% pixel difference exceeds threshold " + threshold + "%.";
        }
        return "Visual validation passed for " + scenarioName + " / " + phase + ": diff "
                + diffPercent + "% is within threshold " + threshold + "%.";
    }

    public static void saveAndCompareAgainstBaseline(Page page, String scenarioName, String phase, double maxDiffPercent) throws IOException {
        boolean strictMode = Boolean.getBoolean("visual.strict");

        Path actualPath = saveScreenshot(page, scenarioName, phase);
        Path baselineDir = Paths.get("test-output", "baselines", scenarioName, phase);
        Path baselinePath = baselineDir.resolve("screenshot.png");

        Files.createDirectories(baselineDir);
        if (!Files.exists(baselinePath)) {
            Files.copy(actualPath, baselinePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("[Visual baseline created] " + scenarioName + " / " + phase + " -> " + baselinePath);
            return;
        }

        double diffPercent = compareImages(actualPath, baselinePath);
        System.out.println("[Visual diff] " + scenarioName + " / " + phase + " -> " + diffPercent + "% pixel difference");

        if (diffPercent > maxDiffPercent) {
            if (strictMode) {
                throw new IllegalStateException(
                        "Visual regression detected for " + scenarioName + " / " + phase + ": " + diffPercent + "% pixels differ (threshold: " + maxDiffPercent + "%)."
                );
            }
            System.out.println("[Visual warning] baseline drift detected, but validation remains non-blocking for bootstrap. "
                    + "Threshold: " + maxDiffPercent + "% | Actual diff: " + diffPercent + "%");
        }
    }

    public static double compareImages(Path actualPath, Path baselinePath) throws IOException {
        BufferedImage actual = ImageIO.read(actualPath.toFile());
        BufferedImage baseline = ImageIO.read(baselinePath.toFile());

        if (actual == null || baseline == null) {
            throw new IOException("Unable to read image for comparison: " + actualPath + " / " + baselinePath);
        }

        int width = Math.min(actual.getWidth(), baseline.getWidth());
        int height = Math.min(actual.getHeight(), baseline.getHeight());
        int diffPixels = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (actual.getRGB(x, y) != baseline.getRGB(x, y)) {
                    diffPixels++;
                }
            }
        }

        int totalPixels = width * height;
        return totalPixels == 0 ? 0.0 : (diffPixels * 100.0) / totalPixels;
    }

    public static String screenshotAsBase64(Page page) {
        return Base64.getEncoder().encodeToString(page.screenshot());
    }
}
