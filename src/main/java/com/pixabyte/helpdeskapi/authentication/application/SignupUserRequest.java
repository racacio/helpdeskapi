package com.pixabyte.helpdeskapi.authentication.application;

import java.util.UUID;

public record SignupUserRequest(
        UUID id,
        String name,
        String email,
        String password,
        UUID organizationId,
        UUID roleId) {

}
