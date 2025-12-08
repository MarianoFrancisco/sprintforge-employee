package com.sprintforge.employee.employee.domain.valueobject;

public record EmployeeLastName(String value) {
    public EmployeeLastName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("El apellido no puede tener más de 100 caracteres");
        }
    }
}
