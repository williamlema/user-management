package com.user.management.app.exception;

import lombok.Data;

@Data
public class UnRegisteredUserException extends RuntimeException {

    private String message;

    public UnRegisteredUserException(String message){
        super(message);
        this.message = message;
    }
}
