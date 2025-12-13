package com.sprintforge.employee.employee.domain.valueobject;

import java.math.BigDecimal;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmployeePercentage(BigDecimal value) {
    public EmployeePercentage {
        if (value == null) {
            throw new ValidationException("El porcentaje no puede estar vacío");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(new BigDecimal("10")) > 0) {
            throw new ValidationException("El porcentaje debe estar entre 0 y 10");
        }
    }
}
