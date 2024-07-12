package com.pixabyte.helpdeskapi.authorization.infraestructure;

import com.pixabyte.helpdeskapi.authorization.domain.Organization;
import com.pixabyte.helpdeskapi.authorization.domain.OrganizationRepository;
import com.pixabyte.helpdeskapi.authorization.infraestructure.persistence.JpaOrganizationRepository;
import com.pixabyte.helpdeskapi.authorization.infraestructure.persistence.OrganizationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostgreSQLOrganizationRepository implements OrganizationRepository {
    private final JpaOrganizationRepository jpaOrganizationRepository;

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
