package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

public class KnowledgeIndexUtils {
    private static final Set<String> INDEXABLE_EXTENSIONS = Set.of(".md", ".txt", ".feature", ".properties");
    private static final Set<String> EXCLUDED_DIRS = Set.of(".git", ".gradle", ".idea", "build", "node_modules");

    public static List<String> collectProjectKnowledge() throws IOException {
        List<String> docs = new ArrayList<>();
        Path root = Paths.get("").toAbsolutePath().normalize();

        try (Stream<Path> stream = Files.walk(root)) {
            stream.filter(Files::isRegularFile)
                    .filter(KnowledgeIndexUtils::isIndexableFile)
                    .sorted(Comparator.naturalOrder())
                    .forEach(path -> {
                        try {
                            docs.add(formatDocument(path));
                        } catch (IOException e) {
                            throw new IllegalStateException("Unable to read project knowledge file: " + path, e);
                        }
                    });
        } catch (IllegalStateException e) {
            throw new IOException(e.getMessage(), e.getCause());
        }

        return docs;
    }

    public static List<String> collectGroundedKnowledge() throws IOException {
        List<String> docs = new ArrayList<>();
        Path root = Paths.get("").toAbsolutePath().normalize();

        try (Stream<Path> stream = Files.walk(root)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> {
                        if (path.toString().contains("test-output/triage") || isIndexableFile(path)) {
                            return true;
                        }
                        return false;
                    })
                    .sorted(Comparator.naturalOrder())
                    .forEach(path -> {
                        try {
                            docs.add(formatDocument(path));
                        } catch (IOException e) {
                            throw new IllegalStateException("Unable to read grounded knowledge file: " + path, e);
                        }
                    });
        } catch (IllegalStateException e) {
            throw new IOException(e.getMessage(), e.getCause());
        }

        return docs;
    }

    public static List<String> searchKnowledge(String query) throws IOException {
        if (query == null || query.isBlank()) {
            return collectGroundedKnowledge();
        }

        String normalized = query.trim();
        String lowerQuery = normalized.toLowerCase(Locale.ROOT);
        List<String> matches = new ArrayList<>();

        for (String content : collectGroundedKnowledge()) {
            if (content == null) {
                continue;
            }

            String lowerContent = content.toLowerCase(Locale.ROOT);
            if (lowerContent.contains(lowerQuery)) {
                matches.add(extractRelevantSnippet(content, normalized));
            }
        }

        return matches;
    }

    public static String findRelevantContext(String query, int maxCharsPerMatch) throws IOException {
        return findGroundedContext(query, maxCharsPerMatch);
    }

    public static String findGroundedContext(String query, int maxCharsPerMatch) throws IOException {
        List<String> matches = searchKnowledge(query);
        if (matches.isEmpty()) {
            return "No matching grounded project knowledge found for query: " + query;
        }

        StringBuilder result = new StringBuilder();
        for (String match : matches) {
            if (result.length() > 0) {
                result.append(System.lineSeparator()).append("---").append(System.lineSeparator());
            }

            String snippet = match.length() > maxCharsPerMatch ? match.substring(0, maxCharsPerMatch) : match;
            result.append(snippet);
        }

        return result.toString();
    }

    private static boolean isIndexableFile(Path path) {
        if (!Files.isRegularFile(path)) {
            return false;
        }

        String fileName = path.getFileName() == null ? "" : path.getFileName().toString();
        if (fileName.isBlank()) {
            return false;
        }

        for (Path parent = path.getParent(); parent != null; parent = parent.getParent()) {
            String segment = parent.getFileName() == null ? "" : parent.getFileName().toString();
            if (EXCLUDED_DIRS.contains(segment)) {
                return false;
            }
        }

        String lowerName = fileName.toLowerCase(Locale.ROOT);
        return INDEXABLE_EXTENSIONS.stream().anyMatch(lowerName::endsWith);
    }

    private static String formatDocument(Path path) throws IOException {
        String fileLabel = path.toAbsolutePath().normalize().toString();
        String content = Files.readString(path);
        return "[source: " + fileLabel + "]\n" + content;
    }

    private static String extractRelevantSnippet(String content, String query) {
        String lowerContent = content.toLowerCase(Locale.ROOT);
        String lowerQuery = query.toLowerCase(Locale.ROOT);
        int matchIndex = lowerContent.indexOf(lowerQuery);
        if (matchIndex < 0) {
            return content.length() > 600 ? content.substring(0, 600) : content;
        }

        int start = Math.max(0, matchIndex - 180);
        int end = Math.min(content.length(), matchIndex + lowerQuery.length() + 180);
        String snippet = content.substring(start, end).replaceAll("\\s+", " ").trim();
        return snippet.length() > 600 ? snippet.substring(0, 600) : snippet;
    }
}
