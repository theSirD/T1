package ru.isaev.cats.rest.Utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.isaev.cats.rest.Utilities.Exceptions.CatNotFoundExceptions;
import ru.isaev.cats.rest.Utilities.Exceptions.OwnerNotFoundException;

import java.time.LocalDate;
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
}