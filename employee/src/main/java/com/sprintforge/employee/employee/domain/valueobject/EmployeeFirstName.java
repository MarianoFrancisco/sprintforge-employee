package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.employee.common.domain.exception.ValidationException;

public record EmployeeFirstName(String value) {
    public EmployeeFirstName {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El nombre no puede estar vacío");
        }
        if (value.length() > 100) {
            throw new ValidationException("El nombre no puede tener más de 100 caracteres");
        }
    }
}
