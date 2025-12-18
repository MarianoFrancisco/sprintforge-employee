package com.sprintforge.employee.payment.application.exception;

import java.time.LocalDate;

import com.sprintforge.common.application.exception.BusinessException;

public class PaymentDateBeforeLastException extends BusinessException {
    public PaymentDateBeforeLastException(String cui, LocalDate lastDate) {
        super("La fecha del pago no puede ser anterior a la última fecha de pago " + lastDate + " para el empleado con CUI " + cui);
    }
}
