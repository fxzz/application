package com.example.application.util.exception;

public class PasswordMismatchException  extends RuntimeException {
    public PasswordMismatchException (String message) {
        super(message);
    }
}