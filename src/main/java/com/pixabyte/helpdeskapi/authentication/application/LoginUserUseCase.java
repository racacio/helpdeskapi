package com.pixabyte.helpdeskapi.authentication.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pixabyte.helpdeskapi.authentication.domain.AuthenticationFailed;
import com.pixabyte.helpdeskapi.authentication.domain.PasswordEncoder;
import com.pixabyte.helpdeskapi.authentication.domain.TokenGenerator;
import com.pixabyte.helpdeskapi.authentication.domain.User;
import com.pixabyte.helpdeskapi.authentication.domain.UserNotFound;
import com.pixabyte.helpdeskapi.authentication.domain.UserRepository;

@Service
public class LoginUserUseCase {
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;
    private final UserRepository userRepository;

    public LoginUserUseCase(PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator,
            UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
        this.userRepository = userRepository;
    }

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
