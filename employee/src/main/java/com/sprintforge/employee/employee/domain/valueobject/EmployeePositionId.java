package com.sprintforge.employee.employee.domain.valueobject;

import java.util.UUID;

import com.sprintforge.employee.common.domain.exception.ValidationException;

public record EmployeePositionId(UUID value) {
    public EmployeePositionId {
        if (value == null) {
            throw new ValidationException("El empleado debe tener un puesto asignado");
        }
    }
}
