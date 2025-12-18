package com.sprintforge.employee.common.domain.valueobject;

import java.math.BigDecimal;

import com.sprintforge.common.domain.exception.ValidationException;

public record Money(BigDecimal amount) {
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    
    public Money {
        if (amount == null) {
            throw new ValidationException("El monto no puede ser nulo");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("El monto no puede ser negativo: " + amount);
        }
    }
    
    // Factory methods
    public static Money of(long value) {
        return new Money(BigDecimal.valueOf(value));
    }
    
    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }
    
    public static Money of(String value) {
        return new Money(new BigDecimal(value));
    }
    
    public static Money add(Money a, Money b) {
		return new Money(a.amount.add(b.amount));
	}

    public static Money subtract(Money a, Money b) {
		return new Money(a.amount.subtract(b.amount));
	}
    
    // Validaciones
    public boolean isPositiveOrZero() {
        return amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    public boolean isPositive() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }
    
    public boolean isGreaterThan(Money other) {
        return this.amount.compareTo(other.amount) > 0;
    }
    
    public boolean isGreaterThanOrEqual(Money other) {
        return this.amount.compareTo(other.amount) >= 0;
    }
    
    // Operaciones
    public Money plus(Money other) {
        return new Money(this.amount.add(other.amount));
    }
    
    public Money minus(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }
}