package com.sprintforge.employee.position.domain.valueobject;

import com.sprintforge.employee.common.domain.exception.ValidationException;

public record PositionDescription(String value) {
    public PositionDescription {
        if (value.length() > 255) {
            throw new ValidationException("La descripción del puesto no puede tener más de 255 caracteres");
        }
    }
}
