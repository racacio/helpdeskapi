package com.pixabyte.helpdeskapi.authorization.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pixabyte.helpdeskapi.authorization.domain.Organization;
import com.pixabyte.helpdeskapi.authorization.domain.OrganizationNotFound;
import com.pixabyte.helpdeskapi.authorization.domain.OrganizationRepository;
import com.pixabyte.helpdeskapi.authorization.domain.PasswordEncoder;
import com.pixabyte.helpdeskapi.authorization.domain.Role;
import com.pixabyte.helpdeskapi.authorization.domain.RoleNotFound;
import com.pixabyte.helpdeskapi.authorization.domain.RoleRepository;
import com.pixabyte.helpdeskapi.authorization.domain.User;
import com.pixabyte.helpdeskapi.authorization.domain.UserAlreadyExists;
import com.pixabyte.helpdeskapi.authorization.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SignupUserUseCase {
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

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
        User user = User.builder()
                .id(request.id())
                .name(request.name())
                .email(request.email())
                .password(hashedPassword)
                .organizationId(request.organizationId())
                .roleId(request.roleId())
                .build();

        userRepository.save(user);
    }
}
