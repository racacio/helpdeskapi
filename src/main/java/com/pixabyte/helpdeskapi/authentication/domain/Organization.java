package com.pixabyte.helpdeskapi.authentication.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Organization {
    private UUID id;
    private String name;
}
