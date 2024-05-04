package ru.isaev.Service.Utilities.Exceptions;

public class CatNotFoundExceptions extends RuntimeException {
    public CatNotFoundExceptions(String msg) {
        super(msg);
    }
}