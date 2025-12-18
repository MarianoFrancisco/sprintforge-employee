package com.sprintforge.employee.payment.application.exception;

import java.time.LocalDate;

import com.sprintforge.common.application.exception.EntityNotFoundException;

public class PaymentNotFound extends EntityNotFoundException {
    public PaymentNotFound(String cui, LocalDate date) {
        super("Pago no encontrado para el empleado con CUI " + cui + " en la fecha " + date);
    }
}
