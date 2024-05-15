package ru.isaev.Utilities.Exceptions;

public class ResponseNotFoundException extends RuntimeException {
    public ResponseNotFoundException(String msg) {
        super(msg);
    }
}