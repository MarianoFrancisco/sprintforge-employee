package com.sprintforge.employee.position.application.port.in.command;

import java.util.UUID;

public record DeactivatePositionCommand(
        UUID id
) {
}
