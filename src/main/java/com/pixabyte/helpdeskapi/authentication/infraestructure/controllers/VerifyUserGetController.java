package com.pixabyte.helpdeskapi.authentication.infraestructure.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.pixabyte.helpdeskapi.authentication.application.VerifyUserCommand;
import com.pixabyte.helpdeskapi.authentication.application.VerifyUserUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class VerifyUserGetController {

    private final Logger logger = LoggerFactory.getLogger(VerifyUserGetController.class);
    private final VerifyUserUseCase verifyUserUseCase;

    public VerifyUserGetController(VerifyUserUseCase verifyUserUseCase) {
        this.verifyUserUseCase = verifyUserUseCase;
    }

    @GetMapping("/authentication/verify/{userId}")
    public ResponseEntity<Void> verify(@PathVariable("userId") String userId) {
        logger.info("VerifyUserGetController#verify - Verifying user account for id={}", userId);
        var command = new VerifyUserCommand(UUID.fromString(userId));
        verifyUserUseCase.execute(command);
        return ResponseEntity.ok(null);
    }

}
