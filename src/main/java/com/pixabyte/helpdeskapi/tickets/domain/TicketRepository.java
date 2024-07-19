package com.pixabyte.helpdeskapi.tickets.domain;

import java.util.List;

public interface TicketRepository {
    void save(Ticket ticket);

    List<Ticket> findAll();

}
