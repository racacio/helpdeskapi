package com.pixabyte.helpdeskapi.tickets;

import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pixabyte.helpdeskapi.shared.domain.EventBus;
import com.pixabyte.helpdeskapi.tickets.application.CreateTicketCommand;
import com.pixabyte.helpdeskapi.tickets.application.CreateTicketUseCase;
import com.pixabyte.helpdeskapi.tickets.domain.TicketCreated;
import com.pixabyte.helpdeskapi.tickets.domain.TicketRepository;

class CreateTicketUseCaseTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private EventBus eventBus;
    private CreateTicketUseCase createTicketUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        createTicketUseCase = new CreateTicketUseCase(ticketRepository, eventBus);
    }

    @Test
    void shouldCreateTicket() {
        CreateTicketCommand command = new CreateTicketCommand(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Uusario no puede iniciar sesión",
                "El usuario tiene error al iniciar sesión",
                "CREATED",
                1);
        createTicketUseCase.execute(command);
        verify(ticketRepository).save(ArgumentMatchers.any());
        verify(eventBus).publish(ArgumentMatchers.any(TicketCreated.class));
    }

}
