package ru.isaev.Utilities.Exceptions;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(String msg) {
        super(msg);
    }
}