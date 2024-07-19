package com.pixabyte.helpdeskapi.authentication.infraestructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.pixabyte.helpdeskapi.authentication.domain.Role;
import com.pixabyte.helpdeskapi.authentication.domain.RoleRepository;
import com.pixabyte.helpdeskapi.authentication.infraestructure.persistence.JpaRoleRepository;
import com.pixabyte.helpdeskapi.authentication.infraestructure.persistence.RoleEntity;

@Repository
public class PostgreSQLRoleRepository implements RoleRepository {
    private final JpaRoleRepository jpaRoleRepository;

    public PostgreSQLRoleRepository(JpaRoleRepository jpaRoleRepository) {
        this.jpaRoleRepository = jpaRoleRepository;
    }

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
