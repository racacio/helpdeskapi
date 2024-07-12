package com.pixabyte.helpdeskapi.authorization.infraestructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrganizationRepository extends JpaRepository<OrganizationEntity, UUID> {

}
