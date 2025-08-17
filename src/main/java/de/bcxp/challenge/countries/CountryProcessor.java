package de.bcxp.challenge.countries;

import de.bcxp.challenge.filereader.FileParser;
import de.bcxp.challenge.filereader.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryProcessor {

    private static Logger LOG = LoggerFactory.getLogger(CountryProcessor.class);

    private final FileReader fileReader;
    private final FileParser fileParser;

    public CountryProcessor(FileReader fileReader, FileParser fileParser) {
        this.fileReader = fileReader;
        this.fileParser = fileParser;
    }

    public List<Country> processCountryFile(String filePath) {
        try {
            String fileContent = fileReader.readFile(filePath);

            if (fileContent == null || fileContent.trim().isEmpty()) {
                LOG.warn("empty File for filePath: {}", filePath);
                return Collections.emptyList();
            }

            List<Map<String, String>> rawFileData = fileParser.parseFileContent(fileContent, filePath);

            if (rawFileData == null || rawFileData.isEmpty()) {
                LOG.error("empty rawFileData for filePath: {}", filePath);
                return Collections.emptyList();
            }

            return rawFileData.stream().map(CountryMapper::fromMap).filter(Objects::nonNull).collect(Collectors.toList());

        } catch (Exception e) {
            LOG.error("Something went wrong while processing countryFile", e);
            return Collections.emptyList();
        }
    }
}
