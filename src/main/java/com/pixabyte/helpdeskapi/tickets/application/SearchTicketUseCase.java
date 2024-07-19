package com.pixabyte.helpdeskapi.tickets.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pixabyte.helpdeskapi.tickets.domain.Ticket;
import com.pixabyte.helpdeskapi.tickets.domain.TicketRepository;

@Service
public class SearchTicketUseCase {
    private final TicketRepository ticketRepository;

    public SearchTicketUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> execute() {
        return ticketRepository.findAll();
    }
}
