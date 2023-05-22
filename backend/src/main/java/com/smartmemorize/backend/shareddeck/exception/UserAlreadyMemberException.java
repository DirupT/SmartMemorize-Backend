package com.smartmemorize.backend.shareddeck.exception;

public class UserAlreadyMemberException extends RuntimeException {
    public UserAlreadyMemberException(String message) {
        super(message);
    }
}
