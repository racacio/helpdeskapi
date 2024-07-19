package com.pixabyte.helpdeskapi.authentication.infraestructure.controllers;

public record LoginPostRequest(
        String email,
        String password) {

}
