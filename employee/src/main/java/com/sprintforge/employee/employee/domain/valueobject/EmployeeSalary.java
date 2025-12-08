package com.sprintforge.employee.employee.domain.valueobject;

import java.math.BigDecimal;

public record EmployeeSalary(BigDecimal value) {
    public EmployeeSalary {
        if (value == null) {
            throw new IllegalArgumentException("El salario no puede estar vacío");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El salario no puede ser negativo");
        }
    }
}
