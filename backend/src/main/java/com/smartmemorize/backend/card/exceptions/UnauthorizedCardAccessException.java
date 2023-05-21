package com.smartmemorize.backend.card.exceptions;

public class UnauthorizedCardAccessException extends RuntimeException {
    public UnauthorizedCardAccessException(String s) {
        super(s);
    }
}
