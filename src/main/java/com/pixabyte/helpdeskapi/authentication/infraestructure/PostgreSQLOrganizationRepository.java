package com.pixabyte.helpdeskapi.authentication.infraestructure;

import com.pixabyte.helpdeskapi.authentication.domain.Organization;
import com.pixabyte.helpdeskapi.authentication.domain.OrganizationRepository;
import com.pixabyte.helpdeskapi.authentication.infraestructure.persistence.JpaOrganizationRepository;
import com.pixabyte.helpdeskapi.authentication.infraestructure.persistence.OrganizationEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PostgreSQLOrganizationRepository implements OrganizationRepository {
    private final JpaOrganizationRepository jpaOrganizationRepository;

    public PostgreSQLOrganizationRepository(JpaOrganizationRepository jpaOrganizationRepository) {
        this.jpaOrganizationRepository = jpaOrganizationRepository;
    }

    @Override
    public Optional<Organization> findOrganizationById(UUID id) {
        Optional<OrganizationEntity> organizationOpt = jpaOrganizationRepository.findById(id);
        if (organizationOpt.isEmpty()) {
            return Optional.empty();
        }
        OrganizationEntity organizationEntity = organizationOpt.get();
        Organization organization = Organization.builder()
                .id(organizationEntity.getId())
                .name(organizationEntity.getName())
                .build();

        return Optional.of(organization);

    }
}
