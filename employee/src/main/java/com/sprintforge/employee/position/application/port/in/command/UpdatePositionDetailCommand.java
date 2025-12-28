package com.sprintforge.employee.position.application.port.in.command;

import java.util.UUID;

public record UpdatePositionDetailCommand(
        UUID id,
        String name,
        String description
) {
}
