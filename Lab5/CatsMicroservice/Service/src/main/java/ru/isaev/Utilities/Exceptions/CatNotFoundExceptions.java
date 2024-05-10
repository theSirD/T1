package ru.isaev.Utilities.Exceptions;

public class CatNotFoundExceptions extends RuntimeException {
    public CatNotFoundExceptions(String msg) {
        super(msg);
    }
}