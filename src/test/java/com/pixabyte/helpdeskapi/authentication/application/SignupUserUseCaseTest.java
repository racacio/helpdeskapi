package com.pixabyte.helpdeskapi.authentication.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.pixabyte.helpdeskapi.authentication.application.SignupUserRequestMother.randomRequest;
import com.pixabyte.helpdeskapi.authentication.domain.Organization;
import com.pixabyte.helpdeskapi.authentication.domain.OrganizationNotFound;
import com.pixabyte.helpdeskapi.authentication.domain.OrganizationRepository;
import com.pixabyte.helpdeskapi.authentication.domain.PasswordEncoder;
import com.pixabyte.helpdeskapi.authentication.domain.Role;
import com.pixabyte.helpdeskapi.authentication.domain.RoleNotFound;
import com.pixabyte.helpdeskapi.authentication.domain.RoleRepository;
import com.pixabyte.helpdeskapi.authentication.domain.User;
import com.pixabyte.helpdeskapi.authentication.domain.UserAlreadyExists;
import com.pixabyte.helpdeskapi.authentication.domain.UserRegisteredEvent;
import com.pixabyte.helpdeskapi.authentication.domain.UserRepository;
import com.pixabyte.helpdeskapi.shared.domain.EventBus;

class SignupUserUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EventBus eventBus;
    private SignupUserUseCase signupUserUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        signupUserUseCase = new SignupUserUseCase(
                eventBus,
                organizationRepository,
                passwordEncoder,
                roleRepository,
                userRepository);
    }

    @Test
    public void shouldRegisterUser() {
        SignupUserRequest request = randomRequest();
        var organization = Organization
                .builder()
                .id(request.organizationId())
                .build();
        var role = Role.builder()
                .id(request.roleId())
                .build();

        when(userRepository.findUserByEmail(request.email())).thenReturn(Optional.empty());
        when(organizationRepository.findOrganizationById(any())).thenReturn(Optional.of(organization));
        when(roleRepository.findRoleById(any())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any())).thenReturn("superEncodedPassword");

        signupUserUseCase.execute(request);

        verify(userRepository).save(any(User.class));
        verify(eventBus).publish(any(UserRegisteredEvent.class));
    }

    @Test
    void shouldThrowsUserAlreadyExistWhenUserEmailAlreadyExistsInDatabase() {
        SignupUserRequest request = randomRequest();
        User databaseUser = User.builder()
                .email(request.email())
                .build();

        when(userRepository.findUserByEmail(request.email())).thenReturn(Optional.of(databaseUser));
        assertThrows(UserAlreadyExists.class, () -> signupUserUseCase.execute(request));
        verify(userRepository, never()).save(any());
        verify(eventBus, never()).publish(any());
    }

    @Test
    void shouldThrowsOrganizationNotFoundWhenRequestOrganizationIdDoesntExists() {
        SignupUserRequest request = randomRequest();
        when(userRepository.findUserByEmail(request.email())).thenReturn(Optional.empty());
        when(organizationRepository.findOrganizationById(any())).thenReturn(Optional.empty());
        assertThrows(OrganizationNotFound.class, () -> signupUserUseCase.execute(request));
        verify(userRepository, never()).save(any());
        verify(eventBus, never()).publish(any());
    }

    @Test
    void shouldThrowsRoleNotFoundWhenRequestRoleIdDoesntExists() {
        SignupUserRequest request = randomRequest();
        var organization = Organization
                .builder()
                .id(request.organizationId())
                .build();
        when(userRepository.findUserByEmail(request.email())).thenReturn(Optional.empty());
        when(organizationRepository.findOrganizationById(any())).thenReturn(Optional.of(organization));
        when(roleRepository.findRoleById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(RoleNotFound.class, () -> signupUserUseCase.execute(request));
        verify(userRepository, never()).save(any());
        verify(eventBus, never()).publish(any());
    }

}
