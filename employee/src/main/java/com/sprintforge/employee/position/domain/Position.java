package com.sprintforge.employee.position.domain;

import com.sprintforge.employee.position.domain.valueobject.PositionDescription;
import com.sprintforge.employee.position.domain.valueobject.PositionId;
import com.sprintforge.employee.position.domain.valueobject.PositionName;
import lombok.Getter;

import java.util.UUID;

import static java.lang.System.currentTimeMillis;
import static java.util.UUID.randomUUID;

@Getter
public class Position {
    private final PositionId id;
    private PositionName name;
    private PositionDescription description;
    private Boolean isActive;
    private Boolean isDeleted;
    private final Long createdAt;
    private Long updatedAt;

    public Position(
            String name,
            String description
    ) {
        this.id = new PositionId(randomUUID());
        this.name = new PositionName(name);
        this.description = new PositionDescription(description);
        this.isActive = true;
        this.isDeleted = false;
        long now = currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Position(
            UUID id,
            String name,
            String description,
            Boolean isActive,
            Boolean isDeleted,
            Long createdAt,
            Long updatedAt
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
        this.updatedAt = currentTimeMillis();
    }

    public void activate() {
        if (this.isDeleted) {
            throw new IllegalStateException("No se puede activar un puesto eliminado");
        }
        this.isActive = true;
        this.updatedAt = currentTimeMillis();
    }

    public void deactivate() {
        if (this.isDeleted) {
            throw new IllegalStateException("No se puede desactivar un puesto eliminado");
        }
        this.isActive = false;
        this.updatedAt = currentTimeMillis();
    }

    public void delete() {
        this.isDeleted = true;
        this.updatedAt = currentTimeMillis();
    }
}
