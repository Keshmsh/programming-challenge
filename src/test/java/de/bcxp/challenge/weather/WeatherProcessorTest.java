package de.bcxp.challenge.weather;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import de.bcxp.challenge.filereader.FileParser;
import de.bcxp.challenge.filereader.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class WeatherProcessorTest {

    @Mock
    private FileReader fileReader;
    @Mock
    private FileParser fileParser;
    @InjectMocks
    private WeatherProcessor weatherProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherProcessor = new WeatherProcessor(fileReader, fileParser);
    }

    @Test
    @DisplayName("processWeather() - Happy flow")
    void returnWeatherList_whenProcessWeatherFile_givenValidWeatherFile() {
        //given
        List<Map<String, String>> rawWeatherData = Arrays.asList(Map.of("Day", "1", "MxT", "22", "MnT", "14"),
                Map.of("Day", "2", "MxT", "32", "MnT", "20"),
                Map.of("Day", "3", "MxT", "40", "MnT", "26"));

        //when
        when(fileReader.readFile(anyString())).thenReturn("fileContent");
        when(fileParser.parseFileContent(anyString(), anyString())).thenReturn(rawWeatherData);

        List<Weather> result = weatherProcessor.processWeatherFile("anyPath");

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("returns empty List when null file content")
    void returnEmptyList_whenProcessWeatherFile_File_givenNullFileContent() {
        //when
        when(fileReader.readFile(anyString())).thenReturn(null);
        List<Weather> result = weatherProcessor.processWeatherFile("anyPath");

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("returns empty List when empty file content")
    void returnEmptyList_whenProcessWeatherFile_File_givenEmptyFileContent() {
        //when
        when(fileReader.readFile(anyString())).thenReturn("");
        List<Weather> result = weatherProcessor.processWeatherFile("anyPath");

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("returns empty List when null parsed data")
    void returnEmptyList_whenProcessWeatherFile_File_givenNullParsedData() {
        //when
        when(fileReader.readFile(anyString())).thenReturn("fileContent");
        when(fileParser.parseFileContent(anyString(), anyString())).thenReturn(null);

        List<Weather> result = weatherProcessor.processWeatherFile("anyPath");

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("returns empty List when empty parsed data")
    void returnEmptyList_whenProcessWeatherFile_File_givenEmptyParsedData() {
        //when
        when(fileReader.readFile(anyString())).thenReturn("fileContent");
        when(fileParser.parseFileContent(anyString(), anyString())).thenReturn(Collections.emptyList());

        List<Weather> result = weatherProcessor.processWeatherFile("anyPath");

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}