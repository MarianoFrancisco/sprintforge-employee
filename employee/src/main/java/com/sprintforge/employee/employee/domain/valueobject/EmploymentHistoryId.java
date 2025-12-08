package com.sprintforge.employee.employee.domain.valueobject;

import java.util.UUID;

public record EmploymentHistoryId(UUID value) {
    public EmploymentHistoryId {
        if (value == null) {
            throw new IllegalArgumentException("El identificador del historial laboral no puede estar vacío");
        }
    }
}
