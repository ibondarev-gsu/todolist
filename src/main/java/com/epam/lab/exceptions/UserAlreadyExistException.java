package com.epam.lab.exceptions;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(final String message) {
        super(message);
    }
}