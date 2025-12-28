package com.sprintforge.employee.payment.application.mapper;

import com.sprintforge.employee.payment.application.port.out.event.PaidEmployeeIntegrationEvent;
import com.sprintforge.employee.payment.domain.Payment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentIntegrationEventMapper {
    public PaidEmployeeIntegrationEvent from(
            Payment payment
    ) {
        return new PaidEmployeeIntegrationEvent(
                payment.getId().value(),
                payment.getEmployee().getId().value(),
                payment.getDate().value(),
                payment.getBaseSalary().amount(),
                payment.getBonus().amount(),
                payment.getDeduction().amount(),
                payment.getTotal().amount()
        );
    }
}
