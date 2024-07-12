package com.pixabyte.helpdeskapi.authorization.domain;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository {
    Optional<Organization> findOrganizationById(UUID id);
}
