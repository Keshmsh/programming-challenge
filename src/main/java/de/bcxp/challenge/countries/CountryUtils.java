package de.bcxp.challenge.countries;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CountryUtils {

    public static List<Country> getCountriesWithHighestDensity(List<Country> countries) {
        BigDecimal maxDensity = countries.stream().map(Country::getDensity).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        return countries.stream().filter(c -> c.getDensity().compareTo(maxDensity) == 0).collect(Collectors.toList());
    }
}
