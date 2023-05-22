package com.smartmemorize.backend.deckinvitation.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DeckInvitationExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeckInvitationExceptionHandler.class);

    @ExceptionHandler(InvitationNotFoundException.class)
    public ProblemDetail handleInvitationNotFoundException(InvitationNotFoundException e) {
        logger.error(e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Invitation not found");

        return problemDetail;
    }

    @ExceptionHandler(InviteAlreadyExistsException.class)
    public ProblemDetail handleInviteAlreadyExistsException(InviteAlreadyExistsException e) {
        logger.error(e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Invite already sent");

        return problemDetail;
    }

    @ExceptionHandler(UserNotRecipientException.class)
    public ProblemDetail handleUserNotRecipientException(UserNotRecipientException e) {
        logger.error(e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        problemDetail.setTitle("User not recipient");

        return problemDetail;
    }
}
