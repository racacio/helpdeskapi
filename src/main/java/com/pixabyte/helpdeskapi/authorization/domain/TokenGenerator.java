package com.pixabyte.helpdeskapi.authorization.domain;

public interface TokenGenerator {
    String generate(User user);
}
