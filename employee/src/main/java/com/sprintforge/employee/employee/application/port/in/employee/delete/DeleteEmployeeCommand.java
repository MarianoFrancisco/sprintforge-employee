package com.sprintforge.employee.employee.application.port.in.employee.delete;

import java.util.UUID;

public record DeleteEmployeeCommand(
        UUID id
) {
}
