package com.pixabyte.helpdeskapi.authentication.domain;

import com.pixabyte.helpdeskapi.shared.domain.DomainEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private LocalDateTime verifiedAt;

    private List<DomainEvent> events;

    public static User createUser(UUID id, String name, String email, String password, UUID organizationId,
            UUID roleId) {
        User user = User.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .organizationId(organizationId)
                .roleId(roleId)
                .verifiedAt(null)
                .build();

        UserRegisteredEvent event = new UserRegisteredEvent(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getOrganizationId(),
                user.getRoleId());

        if (Objects.isNull(user.events)) {
            user.events = new ArrayList<>();
        }

        user.events.add(event);
        return user;
    }

    public List<DomainEvent> pullEvents() {
        List<DomainEvent> pulledEvents = new ArrayList<>(events);
        events = new ArrayList<>();
        return pulledEvents;
    }
}
