package de.bcxp.challenge;

import de.bcxp.challenge.countries.Country;
import de.bcxp.challenge.countries.CountryProcessor;
import de.bcxp.challenge.countries.CountryUtils;
import de.bcxp.challenge.filereader.FileParser;
import de.bcxp.challenge.filereader.FileReader;
import de.bcxp.challenge.weather.Weather;
import de.bcxp.challenge.weather.WeatherProcessor;
import de.bcxp.challenge.weather.WeatherUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software design. Read:
 * create your own classes and packages as appropriate.
 */
public class ProgrammingChallenge {

    private static final String WEATHER_CSV = "src/main/resources/de/bcxp/challenge/weather.csv";
    private static final String COUNTRY_CSV = "src/main/resources/de/bcxp/challenge/countries.csv";

    private static Logger LOG = LoggerFactory.getLogger(ProgrammingChallenge.class);

    /**
     * This is the main entry method of your program.
     *
     * @param args The CLI arguments passed
     */
    public static void main(String[] args) {
        final FileReader fileReader = new FileReader();
        final FileParser fileParser = new FileParser();

        WeatherProcessor weatherProcessor = new WeatherProcessor(fileReader, fileParser);

        List<Weather> weatherList = weatherProcessor.processWeatherFile(WEATHER_CSV);

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

        CountryProcessor countryProcessor = new CountryProcessor(fileReader, fileParser);

        List<Country> countries = countryProcessor.processCountryFile(COUNTRY_CSV);

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
