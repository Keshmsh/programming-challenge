package de.bcxp.challenge.countries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CountryUtilsTest {

    @Test
    @DisplayName("Happy path testing for getCountriesWithHighestDensity()")
    void returnCountry_whenGetDaysWithSmallestSpread_givenValidCountryList() {
        //given
        List<Country> countries = Arrays.asList(new Country.Builder()
                .name("Austria")
                .population(8926000)
                .area(83855)
                .density(new BigDecimal("106.45"))
                .build(), new Country.Builder().name("Belgium").population(11566041).area(30528).density(new BigDecimal("378.87")).build());

        //when
        List<Country> result = CountryUtils.getCountriesWithHighestDensity(countries);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        Country highestDensityCountry = result.get(0);
        assertEquals("Belgium", highestDensityCountry.getName());
        assertEquals(11566041, highestDensityCountry.getPopulation());
        assertEquals(30528, highestDensityCountry.getArea());
        assertEquals(new BigDecimal("378.87"), highestDensityCountry.getDensity());
    }

    @Test
    @DisplayName("empty Country list")
    void returnEmptyList_whenGetDaysWithSmallestSpread_givenEmpytCountryList() {
        //given
        List<Country> CountryList = Collections.emptyList();

        //when
        List<Country> result = CountryUtils.getCountriesWithHighestDensity(CountryList);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}