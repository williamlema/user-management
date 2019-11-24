package com.user.management.app.exception;

import lombok.Data;

@Data
public class InActiveUserException extends RuntimeException {

    private String message;

    public InActiveUserException(String message){
        super(message);
        this.message = message;
    }
}
