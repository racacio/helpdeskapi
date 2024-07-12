package com.pixabyte.helpdeskapi.authorization.infraestructure.controllers;

public record LoginPostRequest(
        String email,
        String password) {

}
