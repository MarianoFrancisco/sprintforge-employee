package com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event;

import java.util.UUID;

public record EmployeeSuspendedKafkaMessage(
        UUID employeeId,
        String cui,
        String email,
        String fullName,
        String status
) {
}
