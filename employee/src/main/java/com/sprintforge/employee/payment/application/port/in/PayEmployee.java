package com.sprintforge.employee.payment.application.port.in;

import com.sprintforge.employee.payment.domain.Payment;

public interface PayEmployee {
    Payment handle(PayEmployeeCommand command);
}
