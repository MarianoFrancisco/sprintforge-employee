package com.sprintforge.employee.payment.application.exception;

import java.time.LocalDate;

import com.sprintforge.common.application.exception.BusinessException;

public class PaymentBeforeHiringException extends BusinessException {
    public PaymentBeforeHiringException(String cui, LocalDate hiringDate) {
        super("La fecha del pago no puede ser anterior a la fecha de contratación " + hiringDate + " para el empleado con CUI " + cui);
    }
    
}
