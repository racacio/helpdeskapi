package com.pixabyte.helpdeskapi.tickets.application;

import org.springframework.stereotype.Service;

import com.pixabyte.helpdeskapi.shared.domain.EventBus;
import com.pixabyte.helpdeskapi.tickets.domain.Ticket;
import static com.pixabyte.helpdeskapi.tickets.domain.Ticket.createTicket;
import com.pixabyte.helpdeskapi.tickets.domain.TicketRepository;

@Service
public class CreateTicketUseCase {

    private final TicketRepository ticketRepository;
    private final EventBus eventBus;

    public CreateTicketUseCase(TicketRepository ticketRepository, EventBus eventBus) {
        this.ticketRepository = ticketRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateTicketCommand command) {
        Ticket ticket = createTicket(
                command.ticketId(),
                command.title(),
                command.description(),
                command.priority(),
                command.status(),
                command.reporterId(),
                command.assignedTo(),
                command.projectId());

        ticketRepository.save(ticket);
        ticket.pullEvents().forEach(eventBus::publish);
    }
}
