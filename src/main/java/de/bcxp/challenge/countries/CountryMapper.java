package de.bcxp.challenge.countries;

import de.bcxp.challenge.shared.exception.InvalidFileDataException;
import de.bcxp.challenge.shared.utils.MapperUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryMapper {

    private static Logger LOG = LoggerFactory.getLogger(CountryMapper.class);

    private static final String NAME = "Name";
    private static final String POPULATION = "Population";
    private static final String AREA = "Area (km²)";
    private static final List<String> MANDATORY_KEY_LIST = Arrays.asList(NAME, POPULATION, AREA);

    public static Country fromMap(Map<String, String> rawData) {
        try {

            if (MapperUtils.rawDataMissingMandatoryKeys(rawData, MANDATORY_KEY_LIST)) {
                LOG.error("Missing mandatory Key's for rawData: {} - skip data", rawData);
                return null;
            }

            String name = rawData.get(NAME);
            int population = MapperUtils.parseToInt(rawData.get(POPULATION));
            double area = MapperUtils.parseToDouble(rawData.get(AREA));
            BigDecimal density = calculateDensity(population, area);

            return new Country.Builder().name(name).population(population).area(area).density(density).build();
        } catch (Exception e) {
            LOG.error("something went wrong while building Country with data: {}", rawData, e);
            return null;
        }
    }

    private static BigDecimal calculateDensity(int population, double area) throws InvalidFileDataException {
        if (area <= 0 || population < 0) {
            LOG.error("Either country population or area are invalid. Population: {}, Area: {}", population, area);
            throw new InvalidFileDataException("Invalid country population or area");
        }
        return new BigDecimal(population / area).setScale(2, RoundingMode.HALF_UP);
    }
}
