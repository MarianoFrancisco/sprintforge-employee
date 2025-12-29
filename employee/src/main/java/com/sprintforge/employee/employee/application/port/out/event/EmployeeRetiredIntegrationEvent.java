package com.sprintforge.employee.employee.application.port.out.event;

import java.util.UUID;

public record EmployeeRetiredIntegrationEvent(
        UUID employeeId,
        String cui,
        String email,
        String fullName,
        String status
) {
}
