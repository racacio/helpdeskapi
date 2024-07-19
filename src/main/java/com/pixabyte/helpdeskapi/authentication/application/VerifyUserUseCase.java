package com.pixabyte.helpdeskapi.authentication.application;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pixabyte.helpdeskapi.authentication.domain.User;
import com.pixabyte.helpdeskapi.authentication.domain.UserNotFound;
import com.pixabyte.helpdeskapi.authentication.domain.UserRepository;

@Service
public class VerifyUserUseCase {
    private final UserRepository userRepository;

    public VerifyUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(VerifyUserCommand command) {
        Optional<User> userOpt = userRepository.findById(command.userId());
        if (userOpt.isEmpty()) {
            throw new UserNotFound();
        }
        User user = userOpt.get();
        user = user.toBuilder()
                .verifiedAt(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
        userRepository.save(user);
    }

}
