package com.sprintforge.employee.position.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record PositionName(String value) {
    public PositionName {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El nombre del cargo no puede estar vacío");
        }
        if (value.length() > 100) {
            throw new ValidationException("El nombre del cargo no puede tener más de 100 caracteres");
        }
    }
}
