package de.bcxp.challenge.shared.utils;

import de.bcxp.challenge.shared.exception.InvalidFileDataException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperUtils {

    private static Logger LOG = LoggerFactory.getLogger(MapperUtils.class);

    public static boolean rawDataMissingMandatoryKeys(Map<String, String> rawData, List<String> mandatoryKeys) {
        return mandatoryKeys.stream().anyMatch(data -> !rawData.containsKey(data));
    }

    public static int parseToInt(String value) throws InvalidFileDataException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            LOG.error("can not parse value {} to int", value);
            throw new InvalidFileDataException("Invalid value. Value provided can not be parsed to int", nfe);
        }
    }

    public static double parseToDouble(String value) throws InvalidFileDataException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            LOG.error("can not parse value {} to double", value);
            throw new InvalidFileDataException("Invalid value. Value provided can not be parsed to double", nfe);
        }
    }
}
