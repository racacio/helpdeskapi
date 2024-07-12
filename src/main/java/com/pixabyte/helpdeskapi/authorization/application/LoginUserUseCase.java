package com.pixabyte.helpdeskapi.authorization.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pixabyte.helpdeskapi.authorization.domain.AuthenticationFailed;
import com.pixabyte.helpdeskapi.authorization.domain.PasswordEncoder;
import com.pixabyte.helpdeskapi.authorization.domain.TokenGenerator;
import com.pixabyte.helpdeskapi.authorization.domain.User;
import com.pixabyte.helpdeskapi.authorization.domain.UserNotFound;
import com.pixabyte.helpdeskapi.authorization.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginUserUseCase {
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;
    private final UserRepository userRepository;

    public LoginUserResponse execute(LoginUserRequest request) {
        Optional<User> userOpt = userRepository.findUserByEmail(request.email());
        if (userOpt.isEmpty()) {
            throw new UserNotFound();
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AuthenticationFailed();
        }

        String token = tokenGenerator.generate(user);
        return new LoginUserResponse(token);

    }
}
