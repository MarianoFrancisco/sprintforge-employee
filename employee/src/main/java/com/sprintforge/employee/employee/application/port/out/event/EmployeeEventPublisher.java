package com.sprintforge.employee.employee.application.port.out.event;

public interface EmployeeEventPublisher {
    void publishEmployeeCreated(EmployeeCreatedIntegrationEvent event);
}
