package de.bcxp.challenge.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FileUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @Nested
    @DisplayName("Test's for isValidFilePath() - Validation of the File path")
    class FilePathValidation {

        @Test
        @DisplayName("isValidFilePath() - Happy flow")
        void returnsTrue_whenIsValidFilePath_ValidFile() {
            //when
            boolean result = FileUtils.isValidFilePath("src/test/resources/de.bcxp.challenge/weather/weather.csv");

            //then
            assertTrue(result);
        }

        @Test
        @DisplayName("false for path is null")
        void returnFalse_whenIsValidFilePath_givenNullPath() {
            //given
            String expectedMessage = "Path provided (null) is either null or empty";

            //when
            boolean result = FileUtils.isValidFilePath(null);

            //then
            assertFalse(result);
        }

        @Test
        @DisplayName("returns false for empty path")
        void returnsFalse_whenIsValidFilePath_givenEmptyPath() {
            //given
            String expectedMessage = "Path provided () is either null or empty";

            //when
            boolean result = FileUtils.isValidFilePath("");

            //then
            assertFalse(result);
        }

        @Test
        @DisplayName("returns false when file path is missing the fileName")
        void returnsFalse_whenIsValidFilePath_PathIsDirectory() {
            //when
            boolean result = FileUtils.isValidFilePath("src/test/resources/de.bcxp.challenge/");

            //then
            assertFalse(result);
        }

        @Test
        @DisplayName("returns false when file extension is invalid")
        void returnsFalse_whenIsValidFilePath_invalidExtension() {
            //when
            boolean result = FileUtils.isValidFilePath("src/test/resources/de.bcxp.challenge/weather/weathercsv");

            //then
            assertFalse(result);
        }

        @Test
        @DisplayName("returns false for non existing file")
        void returnsFalse_whenIsValidFilePath_NonExistingFile() {
            //when
            boolean result = FileUtils.isValidFilePath("src/test/resources/de.bcxp.challenge/weather/weather.txt");

            //then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Test FileUtils.getFileExtension()")
    class FileExtension {

        @Test
        @DisplayName("getFileExtension() - Happy flow")
        void returnFileExtension_whenGetFileExtension_givenValidFileName() {
            //given
            String fileName = "weather.csv";
            String expectedFileExtension = "csv";

            //when
            String result = FileUtils.getFileExtension(fileName);

            //then
            assertNotNull(result);
            assertEquals(expectedFileExtension, result);
        }

        @Test
        @DisplayName("returns null when file name is null")
        void returnNull_whenGetFileExtension_givenNull() {
            //given
            String fileName = null;

            //when
            String result = FileUtils.getFileExtension(fileName);

            //then
            assertNull(result);
        }

        @Test
        @DisplayName("returns empty Sring when file name is invalid")
        void returnEmptyString_whenGetFileExtension_givenInValidFileName() {
            //given
            String fileName = "weathercsv";

            //when
            String result = FileUtils.getFileExtension(fileName);

            //then
            assertNotNull(result);
            assertEquals("", result);
        }
    }

    @Nested
    @DisplayName("Test's for FileUtils.detectCsvDelimiter()")
    class DetectCsvDelimiterTest {

        @Test
        @DisplayName("FileUtils.detectCsvDelimiter() - Happy flow")
        void returnDetectedDelimiter_whenDetectCsvDelimiter_givenValidCsvString() {
            //given
            String[] lines = new String[]{"Name;Capital;Accession;Population;Area (km²);GDP (US$ M);HDI;MEPs",
                    "Austria;Vienna;1995;8926000;83855;447718;0.922;19"};
            String expectedDelimiter = ";";

            //when
            String result = FileUtils.detectCsvDelimiter(lines);

            //then
            assertNotNull(result);
            assertEquals(expectedDelimiter, result);
        }


    }


}