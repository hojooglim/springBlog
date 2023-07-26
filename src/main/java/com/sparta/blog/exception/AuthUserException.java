package com.sparta.blog.exception;

public class AuthUserException extends RuntimeException{

    public AuthUserException() {
    }

    public AuthUserException(String message) {
        super(message);
    }

}
