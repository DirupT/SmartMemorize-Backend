package com.smartmemorize.backend.card.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CardExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(CardExceptionHandler.class);

    @ExceptionHandler(CardNotFoundException.class)
    public ProblemDetail handleCardNotFoundException(CardNotFoundException e) {
        logger.error(e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Card not found");

        return problemDetail;
    }

    @ExceptionHandler(UnauthorizedCardAccessException.class)
    public ProblemDetail handleUnauthorizedCardAccessException(UnauthorizedCardAccessException e) {
        logger.error(e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle("Unauthorized card access");

        return problemDetail;
    }
}
