package com.sprintforge.employee.employee.domain.valueobject;

public record EmployeePhoneNumber(String value) {
    public EmployeePhoneNumber {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío");
        }
        if (!value.matches("\\d{8}")) {
            throw new IllegalArgumentException("El número de teléfono debe tener 8 dígitos");
        }
    }
}
