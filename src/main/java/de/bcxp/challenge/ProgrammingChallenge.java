package de.bcxp.challenge;

import de.bcxp.challenge.countries.CountryProcessor;
import de.bcxp.challenge.filereader.FileParser;
import de.bcxp.challenge.filereader.FileReader;
import de.bcxp.challenge.weather.WeatherProcessor;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software design. Read:
 * create your own classes and packages as appropriate.
 */
public class ProgrammingChallenge {

    /**
     * This is the main entry method of your program.
     *
     * @param args The CLI arguments passed
     */
    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        FileParser fileParser = new FileParser();
        new Application(new WeatherProcessor(fileReader, fileParser), new CountryProcessor(fileReader, fileParser));
    }
}
