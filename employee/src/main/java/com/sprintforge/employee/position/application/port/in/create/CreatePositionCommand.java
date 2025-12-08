package com.sprintforge.employee.position.application.port.in.create;

public record CreatePositionCommand(
        String name,
        String description
) {
}
