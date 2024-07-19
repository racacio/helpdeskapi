package com.pixabyte.helpdeskapi.notification.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class EmailProperties {
    private UUID userId;
    private String subject;
    private String toEmail;

}
