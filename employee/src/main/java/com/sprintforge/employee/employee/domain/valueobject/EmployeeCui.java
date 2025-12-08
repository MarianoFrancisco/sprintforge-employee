package com.sprintforge.employee.employee.domain.valueobject;

public record EmployeeCui(String value) {
    public EmployeeCui {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El CUI no puede estar vacío");
        }
        if (value.length() != 13) {
            throw new IllegalArgumentException("El CUI debe tener exactamente 13 caracteres");
        }
        if (!value.matches("\\d+")) {
            throw new IllegalArgumentException("El CUI solo debe contener números");
        }
    }
}
