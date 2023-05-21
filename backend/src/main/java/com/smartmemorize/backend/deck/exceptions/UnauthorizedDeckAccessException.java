package com.smartmemorize.backend.deck.exceptions;

public class UnauthorizedDeckAccessException extends RuntimeException {
    public UnauthorizedDeckAccessException(String s) {
        super(s);
    }
}
