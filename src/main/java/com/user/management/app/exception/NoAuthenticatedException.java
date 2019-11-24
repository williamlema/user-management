package com.user.management.app.exception;

import lombok.Data;

@Data
public class NoAuthenticatedException extends RuntimeException {

    private String message;

    public NoAuthenticatedException(String message){
        super(message);
        this.message = message;
    }
}
