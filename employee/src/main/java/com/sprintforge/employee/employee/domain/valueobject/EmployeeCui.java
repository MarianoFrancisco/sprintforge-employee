package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.employee.common.domain.exception.ValidationException;

public record EmployeeCui(String value) {
    public EmployeeCui {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El CUI no puede estar vacío");
        }
        if (value.length() != 13) {
            throw new ValidationException("El CUI debe tener exactamente 13 caracteres");
        }
        if (!value.matches("\\d+")) {
            throw new ValidationException("El CUI solo debe contener números");
        }
    }
}
