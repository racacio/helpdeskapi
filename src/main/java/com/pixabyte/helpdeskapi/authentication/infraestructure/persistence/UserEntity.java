package com.pixabyte.helpdeskapi.authentication.infraestructure.persistence;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    private UUID organizationId;
    private UUID roleId;
    private LocalDateTime verifiedAt;
    private Long createdAt;
    private Long updatedAt;

    @PrePersist()
    public void prepersist() {
        this.createdAt = Instant.now().getEpochSecond();
        this.updatedAt = Instant.now().getEpochSecond();
    }
}
