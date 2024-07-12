package com.pixabyte.helpdeskapi.authorization.infraestructure.security.config;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pixabyte.helpdeskapi.authorization.domain.User;
import com.pixabyte.helpdeskapi.authorization.domain.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String jwtSecret;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(
            @Value("${helpdesk.security.jwt.secret}") String jwtSecret,
            UserRepository userRepository) {
        this.jwtSecret = jwtSecret;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");

        if (Objects.isNull(tokenHeader) || !tokenHeader.startsWith("Bearer ")) {
            logger.warn("No hay token de autenticación");
            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenHeader.substring(7);

        Claims claims = getAllClaims(token);

        String userId = extractUserId(claims, Claims::getSubject);
        Optional<User> userOtp = userRepository.findById(UUID.fromString(userId));
        if (userOtp.isEmpty()) {
            logger.error("No existe el usuario propietario del token");
        } else {
            User user = userOtp.get();
            HelpDeskUserDetails userDetails = HelpDeskUserDetails.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .password(null)
                    .organizationId(user.getOrganizationId())
                    .build();

            UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(upat);
        }

        filterChain.doFilter(request, response);
    }

    private String extractUserId(Claims claims, Function<Claims, String> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    private SecretKey getSignInKey() {
        byte[] secretBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
