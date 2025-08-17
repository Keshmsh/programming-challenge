package de.bcxp.challenge.weather;

import de.bcxp.challenge.shared.exception.InvalidFileDataException;
import de.bcxp.challenge.shared.utils.MapperUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherMapper {

    private static Logger LOG = LoggerFactory.getLogger(WeatherMapper.class);

    private static final String DAY = "Day";
    private static final String MAX_TEMPERATURE = "MxT";
    private static final String MIN_TEMPERATURE = "MnT";
    private static final List<String> MANDATORY_KEY_LIST = Arrays.asList(DAY, MAX_TEMPERATURE, MIN_TEMPERATURE);

    public static Weather fromMap(Map<String, String> rawData) {
        try {

            if (MapperUtils.rawDataMissingMandatoryKeys(rawData, MANDATORY_KEY_LIST)) {
                LOG.error("Missing mandatory Key's for rawData: {} - skip entry", rawData);
                return null;
            }

            int day = MapperUtils.parseToInt(rawData.get(DAY));
            int maxTemp = MapperUtils.parseToInt(rawData.get(MAX_TEMPERATURE));
            int minTemp = MapperUtils.parseToInt(rawData.get(MIN_TEMPERATURE));

            int spread = calculateSpread(minTemp, maxTemp);
            return new Weather.Builder()
                    .dayOfMonth(day)
                    .minimumTemperature(minTemp)
                    .maximumTemperature(maxTemp)
                    .temperatureSpread(spread)
                    .build();
        } catch (Exception e) {
            LOG.error("something went wrong while building Weather with data: {}", rawData, e);
            return null;
        }
    }

    private static int calculateSpread(int minTemp, int maxTemp) throws InvalidFileDataException {
        if (maxTemp < minTemp) {
            LOG.error("max temperature ({}) is lower than min temperature ({})", maxTemp, minTemp);
            throw new InvalidFileDataException("Invalid Temperature data. Max Temperature is lower than min Temperature");
        }
        return maxTemp - minTemp;
    }
}
