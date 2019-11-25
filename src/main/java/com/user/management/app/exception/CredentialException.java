package com.user.management.app.exception;

import lombok.Data;

@Data
public class CredentialException extends RuntimeException {

    private String message;

    public CredentialException(String message){
        super(message);
        this.message = message;
    }
}
