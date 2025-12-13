package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmployeeFullName(String value) {

    public EmployeeFullName(EmployeeFirstName firstName, EmployeeLastName lastName) {
        this(firstName.value() + " " + lastName.value());
    }

    public EmployeeFullName {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El nombre completo no puede estar vacío");
        }
        if (value.length() > 200) {
            throw new ValidationException("El nombre completo no puede tener más de 201 caracteres");
        }
    }
}
