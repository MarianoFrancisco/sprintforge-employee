package com.sprintforge.employee.employee.domain.valueobject;

import java.util.UUID;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmploymentHistoryId(UUID value) {
    public EmploymentHistoryId {
        if (value == null) {
            throw new ValidationException("El identificador del historial laboral no puede estar vacío");
        }
    }
}
