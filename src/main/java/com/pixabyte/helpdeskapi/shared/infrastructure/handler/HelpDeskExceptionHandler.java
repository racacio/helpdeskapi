package com.pixabyte.helpdeskapi.shared.infrastructure.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pixabyte.helpdeskapi.authorization.domain.UserAlreadyExists;

@ControllerAdvice
public class HelpDeskExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(HelpDeskExceptionHandler.class);

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handle(UserAlreadyExists exception) {
        logger.error("UserAlreadyExists occurred - {}", exception.getMessage());
        ErrorResponse error = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
