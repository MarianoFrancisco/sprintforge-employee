package com.sprintforge.employee.payment.application.exception;

import com.sprintforge.common.application.exception.DuplicateEntityException;

public class DuplicatePaymentException extends DuplicateEntityException {
    public DuplicatePaymentException(String cui, String date) {
        super("Pago duplicado para el empleado con CUI " + cui + " en la fecha " + date);
    }
    
}
