package de.bcxp.challenge.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileTypeTest {


    @Test
    @DisplayName("returns valid file type")
    void returnsValidFileType_whenFileType_givenValidFileType() {
        //given
        String fileType = "csv";

        //when
        FileType result = FileType.fileType(fileType);

        //then
        assertNotNull(result);
        assertEquals(FileType.CSV, result);
    }

    @Test
    @DisplayName("returns DEFAULT file type")
    void returnsDefaultFileType_whenFileType_givenNonExistingFileType() {
        //given
        String fileType = "txt";

        //when
        FileType result = FileType.fileType(fileType);

        //then
        assertNotNull(result);
        assertEquals(FileType.DEFAULT, result);
    }
}