package de.bcxp.challenge.filereader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReader {

    private static Logger LOG = LoggerFactory.getLogger(FileReader.class);

    public String readFile(String path) {
        if (FileUtils.isValidFilePath(path)) {
            return getFileContentAsString(path);
        }
        return null;
    }

    private String getFileContentAsString(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException io) {
            LOG.error("Something went wring while reading file; {}", path, io);
            return null;
        }
    }
}
