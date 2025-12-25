package com.sprintforge.employee.employee.application.port.in.command;

import java.util.Set;
import java.util.UUID;

public record ValidateEmployeesCommand(
        Set<UUID> ids
) {
}
