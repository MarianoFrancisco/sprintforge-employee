package com.sprintforge.employee.position.application.port.in.command;

public record CreatePositionCommand(
        String name,
        String description
) {
}
