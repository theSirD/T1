package ru.isaev.Service.Utilities.Exceptions;

public class NotYourProfileException extends RuntimeException {
    public NotYourProfileException(String message) {
        super(message);
    }
}
