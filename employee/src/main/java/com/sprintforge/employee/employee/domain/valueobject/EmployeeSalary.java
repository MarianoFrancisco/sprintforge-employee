package com.sprintforge.employee.employee.domain.valueobject;

import java.math.BigDecimal;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmployeeSalary(BigDecimal value) {
    public EmployeeSalary {
        if (value == null) {
            throw new ValidationException("El salario no puede estar vacío");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("El salario no puede ser negativo");
        }
    }
}
