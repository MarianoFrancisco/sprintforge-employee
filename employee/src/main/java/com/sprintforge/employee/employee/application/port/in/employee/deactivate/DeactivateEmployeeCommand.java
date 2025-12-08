package com.sprintforge.employee.employee.application.port.in.employee.deactivate;

import java.util.UUID;

public record DeactivateEmployeeCommand(
        UUID id
) {
}
