package com.user.management.app.exception;

import lombok.Data;

@Data
public class UserDataAlreadyExistException extends RuntimeException {

    private String message;

    public UserDataAlreadyExistException (String message){
        super(message);
        this.message = message;
    }
}
