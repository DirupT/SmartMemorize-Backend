package com.smartmemorize.backend.deckinvitation.exception;

public class InviteAlreadyExistsException extends RuntimeException {
    public InviteAlreadyExistsException(String message) {
        super(message);
    }
}
