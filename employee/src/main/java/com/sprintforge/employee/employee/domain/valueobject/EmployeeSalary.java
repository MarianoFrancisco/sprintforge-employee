package com.sprintforge.employee.employee.domain.valueobject;

import java.math.BigDecimal;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmployeeSalary(BigDecimal amount) {
    public static EmployeeSalary ZERO = new EmployeeSalary(BigDecimal.ZERO);

    public EmployeeSalary {
        validateAmount(amount);
    }

    public EmployeeSalary increase(BigDecimal increment) {
        validateAmount(increment);
        return new EmployeeSalary(this.amount.add(increment));
    }

    public EmployeeSalary decrease(BigDecimal decrement) {
        validateAmount(decrement);

        if (decrement.compareTo(this.amount) >= 0) {
            throw new ValidationException("El descuento debe ser menor al salario actual");
        }

        return new EmployeeSalary(this.amount.subtract(decrement));
    }

    public boolean isGreaterThan(EmployeeSalary other) {
        return this.amount.compareTo(other.amount) > 0;
    }

    public static EmployeeSalary add(EmployeeSalary a, EmployeeSalary b) {
        return new EmployeeSalary(a.amount.add(b.amount));
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new ValidationException("El salario no puede ser nulo");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("El salario debe ser mayor a cero");
        }
    }
}
