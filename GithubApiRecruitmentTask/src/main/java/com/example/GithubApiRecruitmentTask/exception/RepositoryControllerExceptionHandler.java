package com.example.GithubApiRecruitmentTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RepositoryControllerExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {

        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                badRequest.toString(),
                e.getMessage()
        );
        return new ResponseEntity<>(exceptionResponse, badRequest);
    }

    @ExceptionHandler(value = {RequestHeaderException.class})
    public ResponseEntity<Object> handleRequestHeaderException(RequestHeaderException e) {

        HttpStatus badRequest = HttpStatus.NOT_ACCEPTABLE;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                badRequest.toString(),
                e.getMessage()
        );
        return new ResponseEntity<>(exceptionResponse, badRequest);
    }
}

