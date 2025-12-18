package com.sprintforge.employee.payment.application.port.in;

import java.util.List;

import com.sprintforge.employee.payment.domain.Payment;

public interface GetAllPayments {
    List<Payment> handle(GetAllPaymentsQuery query);
}
