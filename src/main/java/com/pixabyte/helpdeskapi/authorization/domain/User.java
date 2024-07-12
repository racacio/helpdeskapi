package com.pixabyte.helpdeskapi.authorization.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private UUID organizationId;
    private UUID roleId;
}
