package com.sprintforge.employee.payment.application.port.out.event;

public interface PaymentEventPublisher {
    void publishPaidEmployee(PaidEmployeeIntegrationEvent event);
}
