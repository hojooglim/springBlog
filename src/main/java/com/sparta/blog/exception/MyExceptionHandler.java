package com.sparta.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler  {
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<String> NullHandleException(NullPointerException NullEx){
        return new ResponseEntity<>(NullEx.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> IllHandleException(IllegalArgumentException IllegalEx){
        return new ResponseEntity<>(IllegalEx.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> ValHandleException(MethodArgumentNotValidException validationEx){
        return new ResponseEntity<>(validationEx.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
