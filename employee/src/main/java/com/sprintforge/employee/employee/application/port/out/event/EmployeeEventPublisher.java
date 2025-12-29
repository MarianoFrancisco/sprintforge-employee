package com.sprintforge.employee.employee.application.port.out.event;

public interface EmployeeEventPublisher {
    void publishEmployeeCreated(EmployeeCreatedIntegrationEvent event);

    void publishEmployeeRetired(EmployeeRetiredIntegrationEvent event);

    void publishEmployeeSuspended(EmployeeSuspendedIntegrationEvent event);

    void publishEmployeeReactivated(EmployeeReactivatedIntegrationEvent event);
}
