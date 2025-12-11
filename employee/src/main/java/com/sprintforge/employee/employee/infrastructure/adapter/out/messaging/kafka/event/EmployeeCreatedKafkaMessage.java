package com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event;

import java.util.UUID;

public record EmployeeCreatedKafkaMessage(
        UUID employeeId,
        String cui,
        String email,
        String firstName,
        String lastName,
        String fullName
) {
}
