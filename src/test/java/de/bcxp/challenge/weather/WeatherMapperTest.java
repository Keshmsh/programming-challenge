package de.bcxp.challenge.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mockStatic;

import de.bcxp.challenge.shared.utils.MapperUtils;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class WeatherMapperTest {

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
    @DisplayName("Happy WeatherMapper.fromMap()")
    void returnWeather_whenFromMap_givenValidWeatherData() {
        //given
        Map<String, String> rawWeatherMap = Map.of("Day", "1", "MxT", "22", "MnT", "14");

        //when
        mapperUtilsStaticMock.when(() -> MapperUtils.rawDataMissingMandatoryKeys(anyMap(), anyList())).thenReturn(false);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("1")).thenReturn(1);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("22")).thenReturn(22);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("14")).thenReturn(14);
        Weather result = WeatherMapper.fromMap(rawWeatherMap);

        //then
        assertNotNull(result);
        assertEquals(1, result.getDayOfMonth());
        assertEquals(22, result.getMaximumTemperature());
        assertEquals(14, result.getMinimumTemperature());
        assertEquals(8, result.getTemperatureSpread());
    }

    @Test
    @DisplayName("Missing Mandatory key")
    void returnNull_whenFromMap_givenMissingMandatoryKey() {
        //given
        Map<String, String> rawWeatherMap = Map.of("MxT", "22", "MnT", "14");

        //when
        mapperUtilsStaticMock.when(() -> MapperUtils.rawDataMissingMandatoryKeys(anyMap(), anyList())).thenReturn(true);
        Weather result = WeatherMapper.fromMap(rawWeatherMap);

        //then
        assertNull(result);
    }

    @Test
    @DisplayName("Invalid file data. MaxTemp lower MinTemp")
    void returnNull_whenFromMap_givenInvalidFileData() {
        //given
        Map<String, String> rawWeatherMap = Map.of("Day", "1", "MxT", "14", "MnT", "22");

        //when
        mapperUtilsStaticMock.when(() -> MapperUtils.rawDataMissingMandatoryKeys(anyMap(), anyList())).thenReturn(false);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("1")).thenReturn(1);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("22")).thenReturn(22);
        mapperUtilsStaticMock.when(() -> MapperUtils.parseToInt("14")).thenReturn(14);
        Weather result = WeatherMapper.fromMap(rawWeatherMap);

        //then
        assertNull(result);
    }
}