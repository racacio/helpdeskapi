package com.pixabyte.helpdeskapi.authorization.infraestructure.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.pixabyte.helpdeskapi.authorization.application.LoginUserRequest;
import com.pixabyte.helpdeskapi.authorization.application.LoginUserUseCase;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
public class LoginPostController {
    private final LoginUserUseCase loginUserUseCase;

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
