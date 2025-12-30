package com.sprintforge.employee.common.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneyWithValidAmount() {
        Money m = new Money(new BigDecimal("10.50"));
        assertEquals(new BigDecimal("10.50"), m.amount());
    }

    @Test
    void shouldThrowWhenAmountIsNull() {
        ValidationException ex = assertThrows(ValidationException.class, () -> new Money(null));
        assertTrue(ex.getMessage().toLowerCase().contains("no puede ser nulo"));
    }

    @Test
    void shouldThrowWhenAmountIsNegative() {
        ValidationException ex = assertThrows(ValidationException.class, () -> new Money(new BigDecimal("-1")));
        assertTrue(ex.getMessage().toLowerCase().contains("no puede ser negativo"));
    }

    @Test
    void shouldCreateUsingFactories() {
        assertEquals(BigDecimal.valueOf(5), Money.of(5).amount());
        assertEquals(BigDecimal.valueOf(2.5), Money.of(2.5).amount());
        assertEquals(new BigDecimal("12.34"), Money.of("12.34").amount());
    }

    @Test
    void shouldAddAndSubtractStatic() {
        Money a = Money.of("10.00");
        Money b = Money.of("3.25");

        assertEquals(new BigDecimal("13.25"), Money.add(a, b).amount());
        assertEquals(new BigDecimal("6.75"), Money.subtract(a, b).amount());
    }

    @Test
    void shouldValidateHelpers() {
        Money zero = Money.ZERO;
        Money pos = Money.of(1);
        Money big = Money.of(10);

        assertTrue(zero.isZero());
        assertTrue(zero.isPositiveOrZero());
        assertFalse(zero.isPositive());

        assertTrue(pos.isPositive());
        assertTrue(pos.isPositiveOrZero());

        assertTrue(big.isGreaterThan(pos));
        assertTrue(big.isGreaterThanOrEqual(pos));
        assertTrue(big.isGreaterThanOrEqual(big));
    }

    @Test
    void shouldPlusAndMinus() {
        Money a = Money.of("10.00");
        Money b = Money.of("3.00");

        assertEquals(new BigDecimal("13.00"), a.plus(b).amount());
        assertEquals(new BigDecimal("7.00"), a.minus(b).amount());
    }

    @Test
    void minusResultNegativeShouldThrow() {
        Money a = Money.of("1.00");
        Money b = Money.of("2.00");

        assertThrows(ValidationException.class, () -> a.minus(b));
    }
}
