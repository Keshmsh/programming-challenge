package de.bcxp.challenge.filereader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileParser {

    private static Logger LOG = LoggerFactory.getLogger(FileParser.class);

    public List<Map<String, String>> parseFileContent(String fileContent, String filePath) {

        String fileExtension = FileUtils.getFileExtension(filePath);

        if (fileExtension == null || fileExtension.isBlank()) {
            LOG.error("No File extension found for filePath: {}", filePath);
            return Collections.emptyList();
        }

        FileType fileType = FileType.fileType(fileExtension);

        switch (fileType) {
            case CSV: {
                return parseCsv(fileContent);
            }
            case JSON: {
                //return parseJson(fileContent);
            }
            default: {
                LOG.error("No implementation found for File extension: {} and path {} - returning empty list", fileType, filePath);
                return Collections.emptyList();
            }
        }
    }

    private List<Map<String, String>> parseCsv(String fileContent) {
        String[] lines = fileContent.split(System.lineSeparator());

        if (lines.length <= 1) {
            LOG.error("No data for FileContent");
            return Collections.emptyList();
        }

        String delimiter = FileUtils.detectCsvDelimiter(lines);

        String[] headers = lines[0].split(delimiter);

        List<Map<String, String>> result = Arrays.stream(lines).skip(1).map(line -> {
            String[] values = line.split(delimiter);
            return IntStream
                    .range(0, headers.length)
                    .boxed()
                    .collect(Collectors.toMap(i -> headers[i], i -> values[i].trim(), (existing, incoming) -> existing));
        }).collect(Collectors.toList());

        return result;
    }
}
