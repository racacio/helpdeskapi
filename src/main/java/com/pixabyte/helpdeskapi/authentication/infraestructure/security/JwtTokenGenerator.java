package com.pixabyte.helpdeskapi.authentication.infraestructure.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pixabyte.helpdeskapi.authentication.domain.TokenGenerator;
import com.pixabyte.helpdeskapi.authentication.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenGenerator implements TokenGenerator {

    private final String jwtSecret;
    private final Long jwtExpiration;

    public JwtTokenGenerator(
            @Value("${helpdesk.security.jwt.secret}") String jwtSecret,
            @Value("${helpdesk.security.jwt.expiration}") Long jwtExpiration) {
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }

    @Override
    public String generate(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] secretBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
