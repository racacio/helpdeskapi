package com.pixabyte.helpdeskapi.authentication.domain;

import java.util.Map;
import java.util.UUID;

import com.pixabyte.helpdeskapi.shared.domain.DomainEvent;

import lombok.Getter;

@Getter
public class UserRegisteredEvent extends DomainEvent {
    private final String name;
    private final String email;
    private final UUID organizationId;
    private final UUID roleId;

    private final String EVENT_NAME = "userRegistered";

    public UserRegisteredEvent(UUID aggregateId, String name, String email, UUID organizationId, UUID roleId) {
        super(aggregateId);
        this.name = name;
        this.email = email;
        this.organizationId = organizationId;
        this.roleId = roleId;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    @Override
    public Map<String, Object> toPrimitives() {
        return null;
    }

}
