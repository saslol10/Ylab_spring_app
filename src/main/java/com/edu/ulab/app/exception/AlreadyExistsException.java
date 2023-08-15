package com.edu.ulab.app.exception;

/**
 * Custom exception
 */
public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
