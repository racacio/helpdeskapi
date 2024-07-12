package com.pixabyte.helpdeskapi.authorization.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Role {
    private UUID id;
    private String name;
}
