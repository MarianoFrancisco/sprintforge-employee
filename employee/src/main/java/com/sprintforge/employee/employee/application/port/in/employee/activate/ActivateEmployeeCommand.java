package com.sprintforge.employee.employee.application.port.in.employee.activate;

import java.util.UUID;

public record ActivateEmployeeCommand(
        UUID id
) {
}
