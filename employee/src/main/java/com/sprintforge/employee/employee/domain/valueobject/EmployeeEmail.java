package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.employee.common.domain.exception.ValidationException;

public record EmployeeEmail(String value) {
    public EmployeeEmail {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El email no puede estar vacío");
        }
        if (value.length() > 100) {
            throw new ValidationException("El email no puede tener más de 100 caracteres");
        }
        if (!value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new ValidationException("El email no tiene un formato válido");
        }
    }
}
