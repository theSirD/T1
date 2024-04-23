package ru.isaev.cats.rest.Utilities.Exceptions;

public class CatNotFoundExceptions extends RuntimeException {
    public CatNotFoundExceptions(String msg) {
        super(msg);
    }
}