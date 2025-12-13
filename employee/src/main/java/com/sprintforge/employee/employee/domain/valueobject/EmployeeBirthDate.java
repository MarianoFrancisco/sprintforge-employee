package com.sprintforge.employee.employee.domain.valueobject;

import java.time.LocalDate;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmployeeBirthDate(LocalDate value) {
    public EmployeeBirthDate {
        if (value != null && value.isAfter(LocalDate.now())) {
            throw new ValidationException("La fecha de nacimiento no puede ser futura");
        }
    }
}
