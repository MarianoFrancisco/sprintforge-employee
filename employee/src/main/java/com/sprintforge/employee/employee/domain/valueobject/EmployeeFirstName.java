package com.sprintforge.employee.employee.domain.valueobject;

public record EmployeeFirstName(String value) {
    public EmployeeFirstName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("El nombre no puede tener más de 100 caracteres");
        }
    }
}
