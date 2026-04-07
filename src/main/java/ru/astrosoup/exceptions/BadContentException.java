package ru.astrosoup.exceptions;

public class BadContentException extends RuntimeException {
    public BadContentException(String message) {
        super(message);
    }
}
