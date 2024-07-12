package com.pixabyte.helpdeskapi.authorization.infraestructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.pixabyte.helpdeskapi.authorization.domain.Role;
import com.pixabyte.helpdeskapi.authorization.domain.RoleRepository;
import com.pixabyte.helpdeskapi.authorization.infraestructure.persistence.JpaRoleRepository;
import com.pixabyte.helpdeskapi.authorization.infraestructure.persistence.RoleEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgreSQLRoleRepository implements RoleRepository {
    private final JpaRoleRepository jpaRoleRepository;

    @Override
    public Optional<Role> findRoleById(UUID id) {
        Optional<RoleEntity> roleOpt = jpaRoleRepository.findById(id);
        if (roleOpt.isEmpty()) {
            return Optional.empty();
        }
        RoleEntity roleEntity = roleOpt.get();
        Role role = Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .build();

        return Optional.of(role);
    }
}
