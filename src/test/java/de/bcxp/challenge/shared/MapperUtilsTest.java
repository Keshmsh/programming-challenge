package de.bcxp.challenge.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.bcxp.challenge.shared.exception.InvalidFileDataException;
import de.bcxp.challenge.shared.utils.MapperUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MapperUtilsTest {

    @Test
    @DisplayName("rawDataMissingMandatoryKeys() - Happy flow")
    void returnFalse_whenRawDataMissingMandatoryKeys_givenValidKeys() {
        //given
        Map<String, String> rawData = Map.of("Day", "1", "MxT", "22", "MnT", "14");
        List<String> mandatoryKeys = Arrays.asList("Day", "MxT", "MnT");

        //when
        boolean result = MapperUtils.rawDataMissingMandatoryKeys(rawData, mandatoryKeys);

        //Then
        assertFalse(result);
    }

    @Test
    @DisplayName("return true when key missing")
    void returnTrue_whenRawDataMissingMandatoryKeys_givenMissingKey() {
        //given
        Map<String, String> rawData = Map.of("MxT", "22", "MnT", "14");
        List<String> mandatoryKeys = Arrays.asList("Day", "MxT", "MnT");

        //when
        boolean result = MapperUtils.rawDataMissingMandatoryKeys(rawData, mandatoryKeys);

        //Then
        assertTrue(result);
    }

    @Test
    @DisplayName("MapperUtils.parseToInt() - Happy flow")
    void returnIntValue_whenParseToInt_givenValidInteger() throws InvalidFileDataException {
        //given
        String value = "12";

        //when
        int result = MapperUtils.parseToInt(value);

        //Then
        assertEquals(12, result);
    }

    @Test
    @DisplayName("throws exception when parsing invalid value to int")
    void ThrowsException_whenParseToInt_givenInvalidInteger() {
        //given
        String value = "ab";
        String expectedMessage = "Invalid value. Value provided can not be parsed to int";

        //when
        InvalidFileDataException result = assertThrows(InvalidFileDataException.class, () -> MapperUtils.parseToInt(value));

        //Then
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    @DisplayName("MapperUtils.parseToDouble() - Happy flow")
    void returnDoubleValue_whenParseToDouble_givenValidDouble() throws InvalidFileDataException {
        //given
        String value = "12.0";

        //when
        double result = MapperUtils.parseToDouble(value);

        //Then
        assertEquals(12.0, result);
    }

    @Test
    @DisplayName("throws exception when parsing invalid value to double")
    void ThrowsException_whenParseToDouble_givenInvalidDouble() {
        //given
        String value = "ab";
        String expectedMessage = "Invalid value. Value provided can not be parsed to double";

        //when
        InvalidFileDataException result = assertThrows(InvalidFileDataException.class, () -> MapperUtils.parseToDouble(value));

        //Then
        assertEquals(expectedMessage, result.getMessage());
    }
}