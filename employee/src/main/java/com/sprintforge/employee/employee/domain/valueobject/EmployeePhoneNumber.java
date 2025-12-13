package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmployeePhoneNumber(String value) {
    public EmployeePhoneNumber {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El número de teléfono no puede estar vacío");
        }
        if (!value.matches("\\d{8}")) {
            throw new ValidationException("El número de teléfono debe tener 8 dígitos");
        }
    }
}
