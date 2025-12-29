package com.sprintforge.employee.payment.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDateTest {

    @Test
    void shouldCreatePaymentDateSuccessfully() {
        PaymentDate date = new PaymentDate(LocalDate.now());

        assertNotNull(date);
        assertEquals(LocalDate.now(), date.value());
    }

    @Test
    void shouldThrowWhenDateIsNull() {
        assertThrows(ValidationException.class, () -> new PaymentDate(null));
    }

    @Test
    void shouldThrowWhenDateIsFuture() {
        LocalDate future = LocalDate.now().plusDays(1);

        assertThrows(ValidationException.class, () -> new PaymentDate(future));
    }
}
