package ru.isaev.cats.rest.Utilities.Exceptions;

public class NotYourProfileException extends RuntimeException {
    public NotYourProfileException(String message) {
        super(message);
    }
}
