package ru.isaev.cats.rest.Utilities.Exceptions;

public class NotYourCatException extends RuntimeException {
    public NotYourCatException(String message) {
        super(message);
    }
}