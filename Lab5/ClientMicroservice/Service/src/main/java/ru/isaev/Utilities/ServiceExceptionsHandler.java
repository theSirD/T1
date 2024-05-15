package ru.isaev.Utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.isaev.Utilities.Exceptions.RequestNotFoundExceptions;
import ru.isaev.Utilities.Exceptions.ResponseNotFoundException;

import java.util.Date;

@ControllerAdvice
public class ServiceExceptionsHandler {

    @ExceptionHandler(RequestNotFoundExceptions.class)
    public ResponseEntity<ErrorMessage> catNotFoundException(RequestNotFoundExceptions ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseNotFoundException.class)
    public ResponseEntity<ErrorMessage> catNotFoundException(ResponseNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }
}



