package com.pixabyte.helpdeskapi.authorization.domain;

public interface PasswordEncoder {
    String encode(String password);

    boolean matches(String rawPassword, String hashedPassword);

}
