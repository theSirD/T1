package ru.isaev.cats.rest.Utilities.Exceptions;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(String msg) {
        super(msg);
    }
}