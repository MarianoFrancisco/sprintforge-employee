package com.sprintforge.employee.employee.domain.valueobject;

public record EmployeeEmail(String value) {
    public EmployeeEmail {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("El email no puede tener más de 100 caracteres");
        }
        if (!value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }
    }
}
