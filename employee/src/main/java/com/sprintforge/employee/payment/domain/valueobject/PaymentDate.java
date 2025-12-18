package com.sprintforge.employee.payment.domain.valueobject;

import java.time.LocalDate;

import com.sprintforge.common.domain.exception.ValidationException;

public record PaymentDate(LocalDate value) {
    public PaymentDate {
        if (value == null) {
            throw new ValidationException("La fecha de pago es obligatoria");
        }
        if (value.isAfter(LocalDate.now())) {
            throw new ValidationException("La fecha de pago no puede ser futura");
        }
    }
}
