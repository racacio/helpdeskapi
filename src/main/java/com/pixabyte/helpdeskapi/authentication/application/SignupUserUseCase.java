package com.pixabyte.helpdeskapi.authentication.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pixabyte.helpdeskapi.authentication.domain.Organization;
import com.pixabyte.helpdeskapi.authentication.domain.OrganizationNotFound;
import com.pixabyte.helpdeskapi.authentication.domain.OrganizationRepository;
import com.pixabyte.helpdeskapi.authentication.domain.PasswordEncoder;
import com.pixabyte.helpdeskapi.authentication.domain.Role;
import com.pixabyte.helpdeskapi.authentication.domain.RoleNotFound;
import com.pixabyte.helpdeskapi.authentication.domain.RoleRepository;
import com.pixabyte.helpdeskapi.authentication.domain.User;
import com.pixabyte.helpdeskapi.authentication.domain.UserAlreadyExists;
import com.pixabyte.helpdeskapi.authentication.domain.UserRepository;
import com.pixabyte.helpdeskapi.shared.domain.EventBus;

@Service
public class SignupUserUseCase {
    private final EventBus eventBus;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public SignupUserUseCase(EventBus eventBus, OrganizationRepository organizationRepository,
            PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.eventBus = eventBus;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void execute(SignupUserRequest request) {
        Optional<User> userOpt = userRepository.findUserByEmail(request.email());
        if (userOpt.isPresent()) {
            throw new UserAlreadyExists("Usuario ya existe con ese email");
        }

        Optional<Organization> organizationOpt = organizationRepository.findOrganizationById(request.organizationId());
        if (organizationOpt.isEmpty()) {
            throw new OrganizationNotFound();
        }

        Optional<Role> roleOpt = roleRepository.findRoleById(request.roleId());
        if (roleOpt.isEmpty()) {
            throw new RoleNotFound();
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        User user = User.createUser(
                request.id(),
                request.name(),
                request.email(),
                hashedPassword,
                request.organizationId(),
                request.roleId());

        userRepository.save(user);
        user.pullEvents().forEach(eventBus::publish);
    }
}
