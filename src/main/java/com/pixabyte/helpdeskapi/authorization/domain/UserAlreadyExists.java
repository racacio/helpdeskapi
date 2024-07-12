package com.pixabyte.helpdeskapi.authorization.domain;

public class UserAlreadyExists extends RuntimeException {
    private final String message;

    public UserAlreadyExists(String message) {
        this.message = message;
    }
}
