package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.employee.common.domain.exception.ValidationException;

public record EmploymentHistoryNotes(String value) {
    public EmploymentHistoryNotes {
        if (value != null && value.length() > 255) {
            throw new ValidationException("Las notas no pueden tener más de 255 caracteres");
        }
    }
}
