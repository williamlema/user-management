package com.user.management.app.exception;

import lombok.Data;

@Data
public class InActiveTokenException extends RuntimeException {

    private String message;

    public InActiveTokenException(String message){
        super(message);
        this.message = message;
    }
}
