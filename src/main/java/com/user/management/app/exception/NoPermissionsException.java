package com.user.management.app.exception;

import lombok.Data;

@Data
public class NoPermissionsException extends RuntimeException {

    private String message;

    public NoPermissionsException(String message){
        super(message);
        this.message = message;
    }
}
