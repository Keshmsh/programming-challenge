package de.bcxp.challenge;

public class TestUtils {

    public static String normalize(String value) {
        return value.replace("\r\n", "\n").replace("\r", "\n");
    }
}
