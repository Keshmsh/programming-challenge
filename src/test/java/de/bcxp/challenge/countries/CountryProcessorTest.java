package de.bcxp.challenge.countries;

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

class CountryProcessorTest {
    @Mock
    private FileReader fileReader;
    @Mock
    private FileParser fileParser;
    @InjectMocks
    private CountryProcessor countryProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryProcessor = new CountryProcessor(fileReader, fileParser);
    }

    @Test
    @DisplayName("processCountryFile() - Happy flow")
    void returnCountryList_whenProcessCountryFile_givenValidCountryFile() {
        //given
        List<Map<String, String>> rawCountryData = Arrays.asList(Map.of("Name", "Austria", "Population", "8926000", "Area (km²)", "83855"),
                Map.of("Name", "Belgium", "Population", "11566041", "Area (km²)", "30528"),
                Map.of("Name", "Bulgaria", "Population", "6916548", "Area (km²)", "110994"));

        //when
        when(fileReader.readFile(anyString())).thenReturn("fileContent");
        when(fileParser.parseFileContent(anyString(), anyString())).thenReturn(rawCountryData);

        List<Country> result = countryProcessor.processCountryFile("anyPath");

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("returns empty List when null file content")
    void returnEmptyList_whenProcessCountryFile_givenNullFileContent() {
        //when
        when(fileReader.readFile(anyString())).thenReturn(null);
        List<Country> result = countryProcessor.processCountryFile("anyPath");

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("returns empty List when empty file content")
    void returnEmptyList_whenProcessCountryFile_givenEmptyFileContent() {
        //when
        when(fileReader.readFile(anyString())).thenReturn("");
        List<Country> result = countryProcessor.processCountryFile("anyPath");

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("returns empty List when null parsed data")
    void returnEmptyList_whenProcessCountryFile_givenNullParsedData() {
        //when
        when(fileReader.readFile(anyString())).thenReturn("fileContent");
        when(fileParser.parseFileContent(anyString(), anyString())).thenReturn(null);

        List<Country> result = countryProcessor.processCountryFile("anyPath");

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("returns empty List when empty parsed data")
    void returnEmptyList_whenProcessCountryFile_givenEmptyParsedData() {
        //when
        when(fileReader.readFile(anyString())).thenReturn("fileContent");
        when(fileParser.parseFileContent(anyString(), anyString())).thenReturn(Collections.emptyList());

        List<Country> result = countryProcessor.processCountryFile("anyPath");

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}