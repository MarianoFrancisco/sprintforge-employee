package com.sprintforge.employee.payment.infrastructure.adapter.out.messaging.kafka.mapper;

import com.sprintforge.employee.payment.application.port.out.event.PaidEmployeeIntegrationEvent;
import com.sprintforge.employee.payment.infrastructure.adapter.out.messaging.kafka.event.PaidEmployeeKafkaMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentKafkaEventMapper {

    public PaidEmployeeKafkaMessage toMessage(PaidEmployeeIntegrationEvent event) {
        return new PaidEmployeeKafkaMessage(
                event.paymentId(),
                event.employeeId(),
                event.date(),
                event.baseSalary(),
                event.bonus(),
                event.deduction(),
                event.total()
        );
    }
}
