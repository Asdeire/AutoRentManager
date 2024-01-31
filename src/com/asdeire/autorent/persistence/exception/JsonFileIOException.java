package com.asdeire.autorent.persistence.exception;

/**
 * Exception representing errors related to handling JSON files.
 *
 * <p>
 * This exception is thrown when an error occurs during the processing of JSON files.
 * It encapsulates various issues that may arise, such as IO errors, parsing errors,
 * or other problems related to working with JSON files.
 * </p>
 */
public class JsonFileIOException extends RuntimeException {

    /**
     * Constructs a new {@code JsonFileIOException} with the specified error message.
     *
     * @param message the error message describing the issue with JSON file handling
     */
    public JsonFileIOException(String message) {
        super(message);
    }
}
