package com.sprintforge.employee.employee.application.port.out.event;

import java.util.UUID;

public record EmployeeReactivatedIntegrationEvent(
        UUID employeeId,
        String cui,
        String email,
        String fullName,
        String status
) {
}
