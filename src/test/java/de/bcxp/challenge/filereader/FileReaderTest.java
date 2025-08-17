package de.bcxp.challenge.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

import de.bcxp.challenge.TestUtils;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class FileReaderTest {

    private FileReader fileReader = new FileReader();
    private static MockedStatic<FileUtils> fileUtilsMockedStatic;

    @BeforeAll
    public static void setUp() {
        fileUtilsMockedStatic = mockStatic(FileUtils.class);
    }

    @AfterAll
    public static void tearDown() {
        fileUtilsMockedStatic.close();
    }

    @Test
    @DisplayName("readFile() - Happy flow")
    void returnValidString_whenReadFile_givenValidCsv() {
        //given
        String expectedResult = "Day,MxT,MnT,AvT,AvDP,1HrP TPcpn,PDir,AvSp,Dir,MxS,SkyC,MxR,Mn,R AvSLP\r\n"
                + "1,88,99,74,53.8,0,280,9.6,270,17,1.6,93,23,1004.5\r\n" + "2,79,63,71,46.5,0,330,8.7,340,23,3.3,70,28,1004.5\r\n"
                + "3,77,55,66,39.6,0,350,5,350,9,2.8,59,24,1016.8";
        fileUtilsMockedStatic.when(() -> FileUtils.isValidFilePath(anyString())).thenReturn(true);

        //when
        String result = fileReader.readFile("src/test/resources/de.bcxp.challenge/weather/weather.csv");

        assertNotNull(result.lines().collect(Collectors.joining("\n")));
        assertEquals(TestUtils.normalize(expectedResult), TestUtils.normalize(result));

    }

    @Test
    @DisplayName("returns null for invalid file path")
    void returnNull_whenReadFile_givenInvalid() {
        //given
        fileUtilsMockedStatic.when(() -> FileUtils.isValidFilePath(anyString())).thenReturn(false);

        //when
        String result = fileReader.readFile("src/test/resources/de.bcxp.challenge/weather/weather.csv");

        assertNull(result);
    }
}