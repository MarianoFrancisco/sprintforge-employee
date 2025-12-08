package com.sprintforge.employee.position.domain.valueobject;

public record PositionName(String value) {
    public PositionName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El nombre del puesto no puede estar vacío");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("El nombre del puesto no puede tener más de 100 caracteres");
        }
    }
}
