package com.sprintforge.employee.position.domain.valueobject;

import java.util.UUID;

import com.sprintforge.common.domain.exception.ValidationException;

public record PositionId(UUID value) {
    public PositionId {
        if (value == null) {
            throw new ValidationException("El identificador del puesto no puede estar vacío");
        }
    }
}
