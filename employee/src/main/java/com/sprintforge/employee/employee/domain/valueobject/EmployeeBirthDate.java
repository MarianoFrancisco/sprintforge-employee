package com.sprintforge.employee.employee.domain.valueobject;

import java.time.LocalDate;
import java.time.Period;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmployeeBirthDate(LocalDate value) {
    private static final int LEGAL_AGE = 18;
    
    public EmployeeBirthDate {
        if (value == null) {
            throw new ValidationException("La fecha de nacimiento es obligatoria");
        }
        if (value.isAfter(LocalDate.now())) {
            throw new ValidationException("La fecha de nacimiento no puede ser futura");
        }
        if (!isLegalAge(value)) {
            throw new ValidationException("El empleado debe ser mayor de edad (" + LEGAL_AGE + " años)");
        }
    }
    
    private static boolean isLegalAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= LEGAL_AGE;
    }
}
