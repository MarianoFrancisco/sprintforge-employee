package com.sprintforge.employee.employee.domain.valueobject;

import java.util.UUID;

public record EmployeeId(UUID value) {
    public EmployeeId {
        if (value == null) {
            throw new IllegalArgumentException("El identificador del empleado no puede estar vacío");
        }
    }
}
