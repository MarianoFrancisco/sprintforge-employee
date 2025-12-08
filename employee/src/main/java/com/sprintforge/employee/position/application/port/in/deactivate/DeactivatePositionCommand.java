package com.sprintforge.employee.position.application.port.in.deactivate;

import java.util.UUID;

public record DeactivatePositionCommand(
        UUID id
) {
}
