package de.bcxp.challenge.weather;

import de.bcxp.challenge.filereader.FileParser;
import de.bcxp.challenge.filereader.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherProcessor {

    private static Logger LOG = LoggerFactory.getLogger(WeatherProcessor.class);

    private final FileReader fileReader;
    private final FileParser fileParser;

    public WeatherProcessor(FileReader fileReader, FileParser fileParser) {
        this.fileReader = fileReader;
        this.fileParser = fileParser;
    }

    //TODO: add javadoc on methods

    /**
     * Process a weather file and creates a list of weather objects
     *
     * @param filePath filePath to weather file
     * @return returns a list of {@link Weather}
     */
    public List<Weather> processWeather(String filePath) {
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

            return rawFileData.stream().map(WeatherMapper::fromMap).filter(Objects::nonNull).collect(Collectors.toList());

        } catch (Exception e) {
            LOG.error("Something went wrong while processing Weather file", e);
            return Collections.emptyList();
        }
    }
}
