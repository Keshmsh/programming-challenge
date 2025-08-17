package de.bcxp.challenge.filereader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {

    private static Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    public static boolean isValidFilePath(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            LOG.error("Path provided ({}) is either null or empty", filePath);
            return false;
        }

        try {
            Path path = Paths.get(filePath);
            if (Files.isDirectory(path)) {
                LOG.error("Path provided is a directory. - {}", filePath);
                return false;
            }

            String name = path.getFileName().toString();
            if (!name.contains(".") || name.startsWith(".") || name.endsWith(".")) {
                LOG.error("No extension or invalid extension format for path: {}", filePath);
                return false;
            }

            File file = path.toFile();
            if (!file.exists()) {
                LOG.error("File for path {} does not exist", filePath);
                return false;
            }
            return true;

        } catch (Exception e) {
            LOG.error("Something went wrong while validating the filePath: {} - {}", filePath, e);
            return false;
        }
    }

    public static String getFileExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex >= 0) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }

    public static String detectCsvDelimiter(String[] lines) {
        char detectedDelimiter = ',';
        for (char delimiter : lines[0].toCharArray()) {
            boolean allRowsHaveEqualColumnCounts = Arrays
                    .stream(lines)
                    .map(line -> line.split(Pattern.quote(String.valueOf(delimiter))))
                    .map(columns -> columns.length)
                    .distinct()
                    .count() == 1;

            if (allRowsHaveEqualColumnCounts) {
                detectedDelimiter = delimiter;
                break;
            }
        }
        return String.valueOf(detectedDelimiter);
    }


}
