package com.todo.exceptions;

public class VerificationTokenNotFountException extends Exception {
    public  VerificationTokenNotFountException(){
        super();
    }

    public VerificationTokenNotFountException(String error){
        super(error);
    }
}