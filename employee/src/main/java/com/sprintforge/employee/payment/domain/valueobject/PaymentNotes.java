package com.sprintforge.employee.payment.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record PaymentNotes(String value) {
    public PaymentNotes {
        if (value != null && value.length() > 255) {
            throw new ValidationException("Las notas no pueden tener más de 255 caracteres");
        }
        value = value != null ? value.trim() : null;
    }
}
