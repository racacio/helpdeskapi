package com.pixabyte.helpdeskapi.authentication.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Role {
    private UUID id;
    private String name;
}
