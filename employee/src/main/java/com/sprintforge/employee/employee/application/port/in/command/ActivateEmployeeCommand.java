package com.sprintforge.employee.employee.application.port.in.command;

import java.util.UUID;

public record ActivateEmployeeCommand(
        UUID id
) {
}
