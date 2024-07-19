package com.pixabyte.helpdeskapi.tickets.domain;

import java.util.Map;
import java.util.UUID;

import com.pixabyte.helpdeskapi.shared.domain.DomainEvent;

import lombok.Getter;

@Getter
public class TicketCreated extends DomainEvent {
    private final String title;
    private final String description;
    private final Integer priority;
    private final String status;
    private final UUID reporterId;
    private final UUID assignedToId;
    private final UUID projectId;

    private final String EVENT_NAME = "ticketCreated";

    public TicketCreated(UUID aggregateId, String title, String description, Integer priority, String status,
            UUID reporterId, UUID assignedToId, UUID projectId) {
        super(aggregateId);
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.reporterId = reporterId;
        this.assignedToId = assignedToId;
        this.projectId = projectId;
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
