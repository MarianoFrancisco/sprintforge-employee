package com.sprintforge.employee.payment.domain.valueobject;

import java.util.UUID;

import com.sprintforge.common.domain.exception.ValidationException;

public record PaymentId(UUID value) {
    public PaymentId {
        if (value == null) {
            throw new ValidationException("El identificador de pago no puede estar vacío");
        }
    }
}
