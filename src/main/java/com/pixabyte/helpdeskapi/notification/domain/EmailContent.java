package com.pixabyte.helpdeskapi.notification.domain;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class EmailContent {
    private String userFullName;
    private String message;

}
