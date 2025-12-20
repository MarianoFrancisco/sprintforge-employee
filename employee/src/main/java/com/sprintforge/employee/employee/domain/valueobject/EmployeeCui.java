package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmployeeCui(String value) {
    public EmployeeCui {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El CUI no puede estar vacío");
        }
        if (!value.matches("\\d{13}")) {
            throw new ValidationException("El CUI debe tener 13 dígitos");
        }
    }
}
