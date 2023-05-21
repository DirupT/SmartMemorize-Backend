package com.smartmemorize.backend.card.exceptions;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String s) {
        super(s);
    }
}
