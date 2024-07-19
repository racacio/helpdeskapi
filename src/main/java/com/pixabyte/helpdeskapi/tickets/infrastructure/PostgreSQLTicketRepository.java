package com.pixabyte.helpdeskapi.tickets.infrastructure;

import java.util.List;

import com.pixabyte.helpdeskapi.tickets.infrastructure.persistence.JpaTicketRepository;
import com.pixabyte.helpdeskapi.tickets.infrastructure.persistence.TicketEntity;
import org.springframework.stereotype.Repository;

import com.pixabyte.helpdeskapi.tickets.domain.Ticket;
import com.pixabyte.helpdeskapi.tickets.domain.TicketRepository;

@Repository
public class PostgreSQLTicketRepository implements TicketRepository {

    private final JpaTicketRepository jpaTicketRepository;

    public PostgreSQLTicketRepository(JpaTicketRepository jpaTicketRepository) {
        this.jpaTicketRepository = jpaTicketRepository;
    }

    @Override
    public void save(Ticket ticket) {
        TicketEntity entity = TicketEntity.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .priority(ticket.getPriority())
                .projectId(ticket.getProjectId())
                .reportToUserId(ticket.getReporterId())
                .assignedToUserId(ticket.getAssignedToId())
                .updatedBy(ticket.getAssignedToId())
                .createdBy(ticket.getReporterId())
                .build();
        jpaTicketRepository.save(entity);
    }

    @Override
    public List<Ticket> findAll() {
        List<TicketEntity> tickets = jpaTicketRepository.findAll();
        return tickets.stream().map(this::toTicket).toList();
    }

    private Ticket toTicket(TicketEntity entity) {
        return Ticket.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .priority(entity.getPriority())
                .reporterId(entity.getReportToUserId())
                .assignedToId(entity.getAssignedToUserId())
                .projectId(entity.getProjectId())
                .build();
    }

}
