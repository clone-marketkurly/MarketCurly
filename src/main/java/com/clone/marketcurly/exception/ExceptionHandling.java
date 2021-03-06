package com.clone.marketcurly.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(value={IllegalArgumentException.class})
    public ResponseEntity<Object> IllegalArgumentExceptionHandle(IllegalArgumentException except){

        Exception exception =new Exception();
        exception.setErrorMessage(except.getMessage());
        exception.setHttpStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> NullPointerExceptionHandle(NullPointerException except){
        Exception exception = new Exception();
        exception.setErrorMessage(except.getMessage());
        exception.setHttpStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class})
    public ResponseEntity<Object> InternalServerErrorHandle(HttpServerErrorException.InternalServerError except){
        Exception exception=new Exception();
        exception.setErrorMessage(except.getMessage());
        exception.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
