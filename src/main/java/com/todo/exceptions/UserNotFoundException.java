package com.todo.exceptions;

public class UserNotFoundException extends Exception {
    public  UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String error){
        super(error);
    }
}
