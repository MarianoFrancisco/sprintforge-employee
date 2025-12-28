package com.sprintforge.employee.employee.application.port.out.event;

import java.util.UUID;

public record EmployeeCreatedIntegrationEvent(
        UUID employeeId,
        String cui,
        String email,
        String firstName,
        String lastName,
        String fullName,
        String status
) {
}
