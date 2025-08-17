package de.bcxp.challenge.shared.exception;

public class InvalidFileDataException extends Exception {
    public InvalidFileDataException(String message) {
        super(message);
    }

    public InvalidFileDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
