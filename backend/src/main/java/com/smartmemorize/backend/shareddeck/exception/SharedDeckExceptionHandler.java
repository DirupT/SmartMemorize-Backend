package com.smartmemorize.backend.shareddeck.exception;

import com.smartmemorize.backend.deckinvitation.exception.InviteAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SharedDeckExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(SharedDeckExceptionHandler.class);

    @ExceptionHandler(UserAlreadyMemberException.class)
    public ProblemDetail handleUserAlreadyMemberException(UserAlreadyMemberException e) {
        logger.error(e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("User already member");

        return problemDetail;
    }
}
