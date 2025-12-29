package com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.mapper;

import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeReactivatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeRetiredIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeSuspendedIntegrationEvent;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeCreatedKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeReactivatedKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeRetiredKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeSuspendedKafkaMessage;
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
                event.fullName(),
                event.status()
        );
    }

    public EmployeeRetiredKafkaMessage toMessage(EmployeeRetiredIntegrationEvent event) {
        return new EmployeeRetiredKafkaMessage(
                event.employeeId(),
                event.cui(),
                event.email(),
                event.fullName(),
                event.status()
        );
    }

    public EmployeeSuspendedKafkaMessage toMessage(EmployeeSuspendedIntegrationEvent event) {
        return new EmployeeSuspendedKafkaMessage(
                event.employeeId(),
                event.cui(),
                event.email(),
                event.fullName(),
                event.status()
        );
    }

    public EmployeeReactivatedKafkaMessage toMessage(EmployeeReactivatedIntegrationEvent event) {
        return new EmployeeReactivatedKafkaMessage(
                event.employeeId(),
                event.cui(),
                event.email(),
                event.fullName(),
                event.status()
        );
    }
}
