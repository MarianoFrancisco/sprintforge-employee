package com.sprintforge.employee.employee.domain.valueobject;

import java.util.UUID;

public record EmployeePosition(
        UUID id,
        String name,
        String description
) {
}
