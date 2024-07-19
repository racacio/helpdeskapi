package com.pixabyte.helpdeskapi.tickets.infrastructure.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pixabyte.helpdeskapi.authentication.infraestructure.security.config.HelpDeskUserDetails;
import com.pixabyte.helpdeskapi.tickets.application.CreateTicketCommand;
import com.pixabyte.helpdeskapi.tickets.application.CreateTicketUseCase;
import com.pixabyte.helpdeskapi.tickets.application.SearchTicketUseCase;
import com.pixabyte.helpdeskapi.tickets.domain.Ticket;

@RestController
public class TicketsController {
    private final CreateTicketUseCase createTicketUseCase;
    private final SearchTicketUseCase searchTicketUseCase;

    public TicketsController(CreateTicketUseCase createTicketUseCase, SearchTicketUseCase searchTicketUseCase) {
        this.createTicketUseCase = createTicketUseCase;
        this.searchTicketUseCase = searchTicketUseCase;
    }

    @GetMapping("/projects/{projectId}/tickets")
    public ResponseEntity<List<Ticket>> getTickets(
            @PathVariable("projectId") String projectId,
            @RequestParam(required = false) Integer priority,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
        return ResponseEntity.ok().body(searchTicketUseCase.execute());
    }

    @PutMapping("/projects/{projectId}/tickets")
    public ResponseEntity<?> createTicket(
            @PathVariable("projectId") String projectId,
            @RequestBody CreateTicketPutRequest body,
            Authentication authentication) {

        HelpDeskUserDetails helpDeskUserDetails = (HelpDeskUserDetails) authentication.getPrincipal();

        CreateTicketCommand command = new CreateTicketCommand(
                body.id(),
                helpDeskUserDetails.getId(),
                body.assignedTo(),
                UUID.fromString(projectId),
                body.title(),
                body.description(),
                body.status(),
                body.priority());

        createTicketUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
