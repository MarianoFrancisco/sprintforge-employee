package com.sprintforge.employee.employee.domain.valueobject;

import java.util.UUID;

import com.sprintforge.employee.common.domain.exception.ValidationException;

public record EmployeeId(UUID value) {
    public EmployeeId {
        if (value == null) {
            throw new ValidationException("El identificador del empleado no puede estar vacío");
        }
    }
}
