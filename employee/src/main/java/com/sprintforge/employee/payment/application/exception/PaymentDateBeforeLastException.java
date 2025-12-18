package com.sprintforge.employee.payment.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

public class PaymentDateBeforeLastException extends BusinessException {
    public PaymentDateBeforeLastException(String cui, String lastDate) {
        super("La fecha del pago no puede ser anterior a la última fecha de pago " + lastDate + " para el empleado con CUI " + cui);
    }
}
