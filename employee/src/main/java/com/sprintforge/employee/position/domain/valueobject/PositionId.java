package com.sprintforge.employee.position.domain.valueobject;

import java.util.UUID;

public record PositionId(UUID value) {
    public PositionId {
        if (value == null) {
            throw new IllegalArgumentException("El identificador del puesto no puede estar vacío");
        }
    }
}
