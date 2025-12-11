package com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.mapper;

import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeCreatedKafkaMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeKafkaEventMapper {

    public EmployeeCreatedKafkaMessage toMessage(EmployeeCreatedIntegrationEvent event) {
        return new EmployeeCreatedKafkaMessage(
                event.employeeId(),
                event.cui(),
                event.email(),
                event.firstName(),
                event.lastName(),
                event.fullName()
        );
    }
}
