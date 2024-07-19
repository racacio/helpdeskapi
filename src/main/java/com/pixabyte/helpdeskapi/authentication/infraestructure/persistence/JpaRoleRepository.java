package com.pixabyte.helpdeskapi.authentication.infraestructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, UUID> {

}