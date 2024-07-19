package com.pixabyte.helpdeskapi.notification.application;

import org.springframework.stereotype.Service;

import com.pixabyte.helpdeskapi.notification.domain.EmailContent;
import com.pixabyte.helpdeskapi.notification.domain.EmailProperties;
import com.pixabyte.helpdeskapi.notification.domain.EmailSender;

@Service
public class SendVerificationEmailUseCase {
    private final EmailSender emailSender;

    public SendVerificationEmailUseCase(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void execute(SendVerificationEmailCommand command) {
        EmailProperties properties = EmailProperties.builder()
                .userId(command.userId())
                .toEmail(command.toEmail())
                .subject("Bienvenidos a HelpDesk de Pixabyte")
                .build();

        EmailContent content = EmailContent.builder()
                .userFullName(command.fullName())
                .message(
                        "Nos alegra tenerte con nosotros, esperamos que tu experiencia sea de la mejor manejando tus incidencias con nosotros")
                .build();

        emailSender.send(properties, content);

    }

}
