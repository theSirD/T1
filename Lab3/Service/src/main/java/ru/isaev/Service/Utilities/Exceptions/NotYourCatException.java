package ru.isaev.Service.Utilities.Exceptions;

public class NotYourCatException extends RuntimeException {
    public NotYourCatException(String message) {
        super(message);
    }
}