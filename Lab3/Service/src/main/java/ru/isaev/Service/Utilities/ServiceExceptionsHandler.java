package ru.isaev.Service.Utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.isaev.Service.Utilities.Exceptions.CatNotFoundExceptions;
import ru.isaev.Service.Utilities.Exceptions.NotYourCatException;
import ru.isaev.Service.Utilities.Exceptions.NotYourProfileException;
import ru.isaev.Service.Utilities.Exceptions.OwnerNotFoundException;

import java.util.Date;

@ControllerAdvice
public class ServiceExceptionsHandler {

    @ExceptionHandler(CatNotFoundExceptions.class)
    public ResponseEntity<ErrorMessage> catNotFoundException(CatNotFoundExceptions ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<ErrorMessage> ownerNotFoundException(CatNotFoundExceptions ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotYourCatException.class)
    public ResponseEntity<ErrorMessage> notYourCatException(NotYourCatException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotYourProfileException.class)
    public ResponseEntity<ErrorMessage> notYourProfileException(NotYourProfileException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }
}