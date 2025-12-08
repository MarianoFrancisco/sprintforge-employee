package com.sprintforge.employee.position.application.port.in.update;

import java.util.UUID;

public record UpdatePositionDetailCommand(
        UUID id,
        String name,
        String description
) {
}
