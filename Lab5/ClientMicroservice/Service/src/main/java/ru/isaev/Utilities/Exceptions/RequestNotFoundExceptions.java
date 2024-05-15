package ru.isaev.Utilities.Exceptions;

public class RequestNotFoundExceptions extends RuntimeException {
    public RequestNotFoundExceptions(String msg) {
        super(msg);
    }
}