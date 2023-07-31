package com.example.application.util.exception;

public class InvalidAccessOperationException extends RuntimeException {
    public InvalidAccessOperationException(String message) {
        super(message);
    }
}