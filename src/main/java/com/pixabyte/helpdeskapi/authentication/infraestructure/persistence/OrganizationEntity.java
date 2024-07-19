package com.pixabyte.helpdeskapi.authentication.infraestructure.persistence;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Table(name = "organizations")
public class OrganizationEntity {
    @Id
    private UUID id;
    private String name;

}