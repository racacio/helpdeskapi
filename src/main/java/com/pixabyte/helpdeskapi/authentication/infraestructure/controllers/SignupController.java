package com.pixabyte.helpdeskapi.authentication.infraestructure.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.pixabyte.helpdeskapi.authentication.application.SignupUserRequest;
import com.pixabyte.helpdeskapi.authentication.application.SignupUserUseCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SignupController {

    private final Logger logger = LoggerFactory.getLogger(SignupController.class);
    private final SignupUserUseCase signupUserUseCase;

    public SignupController(SignupUserUseCase signupUserUseCase) {
        this.signupUserUseCase = signupUserUseCase;
    }

    @PostMapping("/authentication/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupPostRequest body) {
        logger.info("Starting Signup - organizationId={} email={}, password={} id={}, roleId={}",
                body.organizationId(),
                body.email(),
                body.password(),
                body.id(),
                body.roleId());

        signupUserUseCase.execute(new SignupUserRequest(
                body.id(),
                body.name(),
                body.email(),
                body.password(),
                body.organizationId(),
                body.roleId()));

        return ResponseEntity.ok().build();
    }

}
