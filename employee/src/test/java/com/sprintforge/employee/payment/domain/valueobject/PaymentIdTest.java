package com.sprintforge.employee.payment.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentIdTest {

    @Test
    void shouldCreatePaymentIdSuccessfully() {
        UUID id = UUID.randomUUID();
        PaymentId paymentId = new PaymentId(id);

        assertNotNull(paymentId);
        assertEquals(id, paymentId.value());
    }

    @Test
    void shouldThrowWhenIdNull() {
        assertThrows(ValidationException.class, () -> new PaymentId(null));
    }
}
