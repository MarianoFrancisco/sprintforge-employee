package com.sprintforge.employee.position.domain.valueobject;

public record PositionDescription(String value) {
    public PositionDescription {
        if (value.length() > 255) {
            throw new IllegalArgumentException("La descripción del puesto no puede tener más de 255 caracteres");
        }
    }
}
