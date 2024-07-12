package com.pixabyte.helpdeskapi.authorization.infraestructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.pixabyte.helpdeskapi.authorization.domain.User;
import com.pixabyte.helpdeskapi.authorization.domain.UserRepository;
import com.pixabyte.helpdeskapi.authorization.infraestructure.persistence.JpaUserRepository;
import com.pixabyte.helpdeskapi.authorization.infraestructure.persistence.UserEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgreSQLUserRepository implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findById(UUID id) {
        Optional<UserEntity> userOpt = jpaUserRepository.findById(id);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        UserEntity userEntity = userOpt.get();
        User user = User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .organizationId(userEntity.getOrganizationId())
                .roleId(userEntity.getRoleId())
                .build();

        return Optional.of(user);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<UserEntity> userOpt = jpaUserRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        UserEntity userEntity = userOpt.get();
        User user = User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .organizationId(userEntity.getOrganizationId())
                .roleId(userEntity.getRoleId())
                .build();

        return Optional.of(user);
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .organizationId(user.getOrganizationId())
                .roleId(user.getRoleId())
                .build();
        jpaUserRepository.save(userEntity);
    }

}
