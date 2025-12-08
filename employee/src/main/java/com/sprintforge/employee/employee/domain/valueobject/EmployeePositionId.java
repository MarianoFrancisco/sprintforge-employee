package com.sprintforge.employee.employee.domain.valueobject;

import java.util.UUID;

public record EmployeePositionId(UUID value) {
    public EmployeePositionId {
        if (value == null) {
            throw new IllegalArgumentException("El empleado debe tener un puesto asignado");
        }
    }
}
