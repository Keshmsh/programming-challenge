package de.bcxp.challenge.weather;

import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class WeatherUtils {

    public static List<Weather> getDaysWithSmallestSpread(List<Weather> weatherList) {
        OptionalInt minSpread = weatherList.stream().mapToInt(Weather::getTemperatureSpread).min();

        return minSpread.isPresent() ? weatherList
                .stream()
                .filter(w -> w.getTemperatureSpread() == minSpread.getAsInt())
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}
