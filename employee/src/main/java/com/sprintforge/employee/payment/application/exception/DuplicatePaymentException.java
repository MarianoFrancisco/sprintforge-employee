package com.sprintforge.employee.payment.application.exception;

import java.time.LocalDate;

import com.sprintforge.common.application.exception.DuplicateEntityException;

public class DuplicatePaymentException extends DuplicateEntityException {
    public DuplicatePaymentException(String cui, LocalDate date) {
        super("Pago duplicado para el empleado con CUI " + cui + " en la fecha " + date);
    }
    
}
