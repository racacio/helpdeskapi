package com.pixabyte.helpdeskapi.authentication.infraestructure.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.pixabyte.helpdeskapi.authentication.application.LoginUserRequest;
import com.pixabyte.helpdeskapi.authentication.application.LoginUserUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginPostController {
    private final LoginUserUseCase loginUserUseCase;

    public LoginPostController(LoginUserUseCase loginUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/authentication/authenticate")
    public ResponseEntity<LoginPostResponse> login(@RequestBody LoginPostRequest request) {
        LoginUserRequest loginUserRequest = new LoginUserRequest(
                request.email(),
                request.password());
        var tokenResponse = loginUserUseCase.execute(loginUserRequest);
        var response = new LoginPostResponse(tokenResponse.token());
        return ResponseEntity.ok(response);
    }

}
