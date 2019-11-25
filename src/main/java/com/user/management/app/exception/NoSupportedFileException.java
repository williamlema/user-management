package com.user.management.app.exception;

import lombok.Data;

@Data
public class NoSupportedFileException extends RuntimeException {

    private String message;

    public NoSupportedFileException(String message){
        super(message);
        this.message = message;
    }
}
