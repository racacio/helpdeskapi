package com.pixabyte.helpdeskapi.authorization.application;

public record LoginUserRequest(
        String email,
        String password) {

}
