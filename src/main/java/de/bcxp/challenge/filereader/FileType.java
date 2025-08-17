package de.bcxp.challenge.filereader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum FileType {
    CSV, JSON, DEFAULT;

    private static Logger LOG = LoggerFactory.getLogger(FileType.class);


    public static FileType fileType(String fileType) {
        try {
            return FileType.valueOf(fileType.toUpperCase());
        } catch (Exception e) {
            LOG.error("Something went wrong while processing file type for: {}", fileType, e);
            return DEFAULT;
        }
    }
}
