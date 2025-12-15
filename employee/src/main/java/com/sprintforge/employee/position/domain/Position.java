package com.sprintforge.employee.position.domain;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.employee.position.domain.valueobject.PositionDescription;
import com.sprintforge.employee.position.domain.valueobject.PositionId;
import com.sprintforge.employee.position.domain.valueobject.PositionName;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Getter
public class Position {
    private final PositionId id;
    private PositionName name;
    private PositionDescription description;
    private boolean isActive;
    private boolean isDeleted;
    private final Instant createdAt;
    private Instant updatedAt;

    public Position(
            String name,
            String description
    ) {
        this.id = new PositionId(randomUUID());
        this.name = new PositionName(name);
        this.description = new PositionDescription(description);
        this.isActive = true;
        this.isDeleted = false;
        Instant timestamp = now();

        this.createdAt = timestamp;
        this.updatedAt = timestamp;
    }

    public Position(
            UUID id,
            String name,
            String description,
            Boolean isActive,
            Boolean isDeleted,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = new PositionId(id);
        this.name = new PositionName(name);
        this.description = new PositionDescription(description);
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateDetails(String name, String description) {
        this.name = new PositionName(name);
        this.description = new PositionDescription(description);
        this.updatedAt = now();
    }

    public void activate() {
        if (this.isDeleted) {
            throw new ValidationException("No se puede activar un cargo eliminado");
        }
        if (this.isActive) {
            throw new ValidationException("El cargo ya está activo");
        }
        this.isActive = true;
        this.updatedAt = now();
    }

    public void deactivate() {
        if (this.isDeleted) {
            throw new ValidationException("No se puede desactivar un cargo eliminado");
        }
        if (!this.isActive) {
            throw new ValidationException("El cargo ya está inactivo");
        }
        this.isActive = false;
        this.updatedAt = now();
    }

    public void delete() {
        if (this.isDeleted) {
            throw new ValidationException("El cargo ya está eliminado");
        }
        this.isDeleted = true;
        this.updatedAt = now();
    }
}
