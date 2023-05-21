package com.smartmemorize.backend.deck.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DeckExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(DeckExceptionHandler.class);

    @ExceptionHandler(DeckNotFoundException.class)
    public ProblemDetail handleDeckNotFoundException(DeckNotFoundException e) {
        logger.error(e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Deck not found");

        return problemDetail;
    }

    @ExceptionHandler(UnauthorizedDeckAccessException.class)
    public ProblemDetail handleUnauthorizedDeckAccessException(UnauthorizedDeckAccessException e) {
        logger.error(e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle("Unauthorized deck access");

        return problemDetail;
    }
}
