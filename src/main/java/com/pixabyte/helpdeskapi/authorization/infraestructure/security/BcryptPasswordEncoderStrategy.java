package com.pixabyte.helpdeskapi.authorization.infraestructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pixabyte.helpdeskapi.authorization.domain.PasswordEncoder;

@Service
public class BcryptPasswordEncoderStrategy implements PasswordEncoder {

    private final BCryptPasswordEncoder encoder;

    public BcryptPasswordEncoderStrategy() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }

}
