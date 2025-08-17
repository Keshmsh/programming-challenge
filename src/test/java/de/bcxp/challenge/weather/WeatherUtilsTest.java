package de.bcxp.challenge.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeatherUtilsTest {

    @Test
    @DisplayName("Happy path testing for getDaysWithSmallestSpread()")
    void returnWeather_whenGetDaysWithSmallestSpread_givenValidWeatherList() {
        //given
        List<Weather> weatherList = Arrays.asList(new Weather.Builder()
                .dayOfMonth(1)
                .maximumTemperature(88)
                .minimumTemperature(59)
                .temperatureSpread(29)
                .build(), new Weather.Builder().dayOfMonth(2).maximumTemperature(79).minimumTemperature(63).temperatureSpread(16).build());

        //when
        List<Weather> result = WeatherUtils.getDaysWithSmallestSpread(weatherList);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        Weather smallestSpreadWeather = result.get(0);
        assertEquals(2, smallestSpreadWeather.getDayOfMonth());
        assertEquals(79, smallestSpreadWeather.getMaximumTemperature());
        assertEquals(63, smallestSpreadWeather.getMinimumTemperature());
        assertEquals(16, smallestSpreadWeather.getTemperatureSpread());
    }

    @Test
    @DisplayName("empty weather list")
    void returnEmptyList_whenGetDaysWithSmallestSpread_givenEmpytWeatherList() {
        //given
        List<Weather> weatherList = Collections.emptyList();

        //when
        List<Weather> result = WeatherUtils.getDaysWithSmallestSpread(weatherList);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}