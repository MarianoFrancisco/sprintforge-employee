package com.sprintforge.employee.payment.application.port.out.persistence;

import com.sprintforge.employee.payment.domain.Payment;

public interface SavePayment {
    Payment save(Payment payment);
}
