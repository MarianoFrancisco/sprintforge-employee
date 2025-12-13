package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmployeeLastName(String value) {
    public EmployeeLastName {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El apellido no puede estar vacío");
        }
        if (value.length() > 100) {
            throw new ValidationException("El apellido no puede tener más de 100 caracteres");
        }
    }
}
