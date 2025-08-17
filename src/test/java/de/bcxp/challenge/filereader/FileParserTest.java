package de.bcxp.challenge.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class FileParserTest {

    private final FileParser fileParser = new FileParser();
    private static MockedStatic<FileUtils> fileUtilsStaticMock;

    private static final String FILE_EXTENSION_CSV = "CSV";
    private static final String CSV_DELIMITER = ",";

    @BeforeAll
    public static void setUp() {
        fileUtilsStaticMock = mockStatic(FileUtils.class);
    }

    @AfterAll
    public static void tearDown() {
        fileUtilsStaticMock.close();
    }

    @Nested
    @DisplayName("Test's for parseFileContent() - csv")
    class parseCsvFileTest {

        @Test
        @DisplayName("parseFileContent() - Happy flow")
        void returnParsedFileContent_whenParseFileContent_givenValidCsvFile() throws IOException {
            //given
            String filePath = "src/test/resources/de.bcxp.challenge/weather/weather.csv";
            String fileContent = Files.readString(Paths.get(filePath));

            //when
            fileUtilsStaticMock.when(() -> FileUtils.getFileExtension(anyString())).thenReturn(FILE_EXTENSION_CSV);
            fileUtilsStaticMock.when(() -> FileUtils.detectCsvDelimiter(any(String[].class))).thenReturn(CSV_DELIMITER);

            List<Map<String, String>> result = fileParser.parseFileContent(fileContent, filePath);

            //then
            assertNotNull(result);
            assertEquals(3, result.size());
        }

        @Test
        @DisplayName("returns empty list for null file extension")
        void returnEmptyList_whenParseFileContent_givenNullFileExtension() throws IOException {
            //given
            String filePath = "src/test/resources/de.bcxp.challenge/weather/weather.csv";
            String fileContent = Files.readString(Paths.get(filePath));

            //when
            fileUtilsStaticMock.when(() -> FileUtils.getFileExtension(anyString())).thenReturn(null);

            List<Map<String, String>> result = fileParser.parseFileContent(fileContent, filePath);

            //then
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("returns empty list for empty file extension")
        void returnEmptyList_whenParseFileContent_givenEmptyFileExtension() throws IOException {
            //given
            String filePath = "src/test/resources/de.bcxp.challenge/weather/weather.csv";
            String fileContent = Files.readString(Paths.get(filePath));

            //when
            fileUtilsStaticMock.when(() -> FileUtils.getFileExtension(anyString())).thenReturn("");

            List<Map<String, String>> result = fileParser.parseFileContent(fileContent, filePath);

            //then
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("returns empty list for not implemented file extension")
        void returnEmptyList_whenParseFileContent_givenNoImplementationFileType() throws IOException {
            //given
            String filePath = "src/test/resources/de.bcxp.challenge/weather/weather.csv";
            String fileContent = Files.readString(Paths.get(filePath));

            //when
            fileUtilsStaticMock.when(() -> FileUtils.getFileExtension(anyString())).thenReturn("fallback");

            List<Map<String, String>> result = fileParser.parseFileContent(fileContent, filePath);

            //then
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("returns empty list for empty file content")
        void returnEmptyList_whenParseFileContent_givenEmptyFileContent() {
            //given
            String filePath = "src/test/resources/de.bcxp.challenge/weather/weather.csv";
            String fileContent = "";

            //when
            fileUtilsStaticMock.when(() -> FileUtils.getFileExtension(anyString())).thenReturn(FILE_EXTENSION_CSV);

            List<Map<String, String>> result = fileParser.parseFileContent(fileContent, filePath);

            //then
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }


}