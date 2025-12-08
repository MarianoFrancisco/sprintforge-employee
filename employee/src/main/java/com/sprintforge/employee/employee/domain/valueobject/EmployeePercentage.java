package com.sprintforge.employee.employee.domain.valueobject;

import java.math.BigDecimal;

public record EmployeePercentage(BigDecimal value) {
    public EmployeePercentage {
        if (value == null) {
            throw new IllegalArgumentException("El porcentaje no puede estar vacío");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }
    }
}
