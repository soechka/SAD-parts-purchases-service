package com.example.sadbe.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends RuntimeException{

    public NotFoundException(Throwable cause) {
        super(cause);
    }


}
