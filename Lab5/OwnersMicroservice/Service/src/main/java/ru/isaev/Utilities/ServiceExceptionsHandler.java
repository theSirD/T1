package ru.isaev.Utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.isaev.Utilities.Exceptions.NotYourProfileException;
import ru.isaev.Utilities.Exceptions.OwnerNotFoundException;

import java.util.Date;

@ControllerAdvice
public class ServiceExceptionsHandler {

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<ErrorMessage> ownerNotFoundException(OwnerNotFoundException ex) {
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