package com.pixabyte.helpdeskapi.authorization.infraestructure.controllers;

import java.util.UUID;

public record SignupPostRequest(
        UUID id,
        String name,
        String email,
        String password,
        UUID organizationId,
        UUID roleId) {

}
