package de.bcxp.challenge.countries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mockStatic;

import de.bcxp.challenge.shared.utils.MapperUtils;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class CountryMapperTest {

    private static MockedStatic<MapperUtils> mapperUtilsStaticMock;

    @BeforeAll
    public static void setUp() {
        mapperUtilsStaticMock = mockStatic(MapperUtils.class);
    }

    @AfterAll
    public static void tearDown() {
        mapperUtilsStaticMock.close();
    }


    @Test
    @DisplayName("Happy")
    void returnCountry_whenFromMap_givenValidCountryData() {
        //given
        Map<String, String> rawCountryMap = Map.of("Name", "Austria", "Population", "8926000", "Area (km²)", "83855");

        //when
        mapperUtilsStaticMock.when(() -> MapperUtils.rawDataMissingMandatoryKeys(anyMap(), anyList())).thenReturn(false);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("8926000")).thenReturn(8926000);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToDouble("83855")).thenReturn(83855.0);
        Country result = CountryMapper.fromMap(rawCountryMap);

        //then
        assertNotNull(result);
        assertEquals("Austria", result.getName());
        assertEquals(8926000, result.getPopulation());
        assertEquals(83855.0, result.getArea());
        assertEquals(new BigDecimal("106.45"), result.getDensity());
    }

    @Test
    @DisplayName("Missing Mandatory key")
    void returnNull_whenFromMap_givenMissingMandatoryKey() {
        //given
        Map<String, String> rawCountryMap = Map.of("Population", "8926000", "Area (km²)", "83855");

        //when
        mapperUtilsStaticMock.when(() -> MapperUtils.rawDataMissingMandatoryKeys(anyMap(), anyList())).thenReturn(true);
        Country result = CountryMapper.fromMap(rawCountryMap);

        //then
        assertNull(result);
    }

    @Test
    @DisplayName("Invalid file data. MaxTemp lower MinTemp")
    void returnNull_whenFromMap_givenInvalidPopulation() {
        //given
        Map<String, String> rawCountryMap = Map.of("Name", "Austria", "Population", "0", "Area (km²)", "83855");

        //when
        mapperUtilsStaticMock.when(() -> MapperUtils.rawDataMissingMandatoryKeys(anyMap(), anyList())).thenReturn(false);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("1")).thenReturn(1);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("22")).thenReturn(22);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("14")).thenReturn(14);
        Country result = CountryMapper.fromMap(rawCountryMap);

        //then
        assertNull(result);
    }

    @Test
    @DisplayName("Invalid file data. MaxTemp lower MinTemp")
    void returnNull_whenFromMap_givenInvalidArea() {
        //given
        Map<String, String> rawCountryMap = Map.of("Name", "Austria", "Population", "8926000", "Area (km²)", "0");

        //when
        mapperUtilsStaticMock.when(() -> MapperUtils.rawDataMissingMandatoryKeys(anyMap(), anyList())).thenReturn(false);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("1")).thenReturn(1);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("22")).thenReturn(22);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("14")).thenReturn(14);
        Country result = CountryMapper.fromMap(rawCountryMap);

        //then
        assertNull(result);
    }
}