package com.pixabyte.helpdeskapi.authorization.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Organization {
    private UUID id;
    private String name;
}
