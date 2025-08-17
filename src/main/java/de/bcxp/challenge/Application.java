package de.bcxp.challenge;

import de.bcxp.challenge.countries.Country;
import de.bcxp.challenge.countries.CountryProcessor;
import de.bcxp.challenge.countries.CountryUtils;
import de.bcxp.challenge.weather.Weather;
import de.bcxp.challenge.weather.WeatherProcessor;
import de.bcxp.challenge.weather.WeatherUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static Logger LOG = LoggerFactory.getLogger(Application.class);

    private static final String WEATHER_CSV = "src/main/resources/de/bcxp/challenge/weather.csv";
    private static final String COUNTRY_CSV = "src/main/resources/de/bcxp/challenge/countries.csv";

    private final WeatherProcessor weatherProcessor;
    private final CountryProcessor countryProcessor;

    public Application(WeatherProcessor weatherProcessor, CountryProcessor countryProcessor) {
        this.weatherProcessor = weatherProcessor;
        this.countryProcessor = countryProcessor;
    }

    public void run() {

        List<Weather> weatherList = this.weatherProcessor.processWeatherFile(WEATHER_CSV);

        if (!weatherList.isEmpty()) {
            List<Weather> minSpreadWeather = WeatherUtils.getDaysWithSmallestSpread(weatherList);

            System.out.printf("found %s Day(s) with smallest temperature spread\n", minSpreadWeather.size());
            for (Weather weather : minSpreadWeather) {
                String dayWithSmallestTempSpread = String.valueOf(weather.getDayOfMonth());
                System.out.printf("smallest temperature spread Day: %s%n", dayWithSmallestTempSpread);
            }
        } else {
            LOG.debug("empty weatherList");
        }

        List<Country> countries = this.countryProcessor.processCountryFile(COUNTRY_CSV);

        if (!countries.isEmpty()) {
            List<Country> maxDensityCountries = CountryUtils.getCountriesWithHighestDensity(countries);

            System.out.printf("found %s Countrie(s) with highest density\n", maxDensityCountries.size());
            for (Country country : maxDensityCountries) {
                String countryWithHighestPopulationDensity = String.valueOf(country.getName());     // Your day analysis function call …
                System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
            }
        } else {
            LOG.debug("empty countriesList");
        }
    }
}
