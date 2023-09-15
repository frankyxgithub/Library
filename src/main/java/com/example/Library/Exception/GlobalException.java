package com.example.Library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class GlobalException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidInputException(MethodArgumentNotValidException exception){
        Map<String, String> errorDetails = new HashMap<>();
//        Lambda Version
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> errorDetails.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return errorDetails;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> HandleExceptions(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There has been an internal server error");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException resourceNotFoundException){
        return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("User could not be found");
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException resourceNotFoundException){
        return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("Book could not be found");
    }

}


