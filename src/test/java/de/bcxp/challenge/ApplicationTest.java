package de.bcxp.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import de.bcxp.challenge.countries.Country;
import de.bcxp.challenge.countries.CountryProcessor;
import de.bcxp.challenge.countries.CountryUtils;
import de.bcxp.challenge.weather.Weather;
import de.bcxp.challenge.weather.WeatherProcessor;
import de.bcxp.challenge.weather.WeatherUtils;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

class ApplicationTest {

    @Mock
    private WeatherProcessor weatherProcessor;
    @Mock
    private CountryProcessor countryProcessor;

    private static MockedStatic<WeatherUtils> weatherUtilsStaticMock;
    private static MockedStatic<CountryUtils> countryUtilsStaticMock;

    private Application app;


    @BeforeAll
    public static void setUp() {
        weatherUtilsStaticMock = mockStatic(WeatherUtils.class);
        countryUtilsStaticMock = mockStatic(CountryUtils.class);
    }

    @AfterAll
    public static void tearDown() {
        weatherUtilsStaticMock.close();
        countryUtilsStaticMock.close();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        app = new Application(weatherProcessor, countryProcessor);
    }

    @Test
    @DisplayName("Application.run() - Happy flow")
    void run() {
        //given
        String expectedOutput = "found 1 Day(s) with smallest temperature spread\n" + "smallest temperature spread Day: 1\n"
                + "found 1 Countrie(s) with highest density\n" + "Country with highest population density: Austria\n";
        Weather weather = new Weather.Builder().dayOfMonth(1).maximumTemperature(10).minimumTemperature(-5).temperatureSpread(15).build();
        Country country = new Country.Builder().name("Austria").population(8926000).area(83855).density(new BigDecimal("106.45")).build();

        //when
        when(weatherProcessor.processWeatherFile(anyString())).thenReturn(List.of(weather));
        weatherUtilsStaticMock.when(() -> WeatherUtils.getDaysWithSmallestSpread(anyList())).thenReturn(List.of(weather));
        when(countryProcessor.processCountryFile(anyString())).thenReturn(List.of(country));
        countryUtilsStaticMock.when(() -> CountryUtils.getCountriesWithHighestDensity(anyList())).thenReturn(List.of(country));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        app.run();

        String output = out.toString();
        assertNotNull(output);
        assertEquals(TestUtils.normalize(expectedOutput), TestUtils.normalize(output));
    }
}