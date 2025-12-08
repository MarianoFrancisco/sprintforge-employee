package com.sprintforge.employee.employee.domain.valueobject;

import java.time.LocalDate;

public record EmployeeBirthDate(LocalDate value) {
    public EmployeeBirthDate {
        if (value != null && value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
    }
}
