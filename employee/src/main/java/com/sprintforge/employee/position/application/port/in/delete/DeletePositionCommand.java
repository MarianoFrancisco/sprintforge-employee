package com.sprintforge.employee.position.application.port.in.delete;

import java.util.UUID;

public record DeletePositionCommand(
        UUID id
) {
}
