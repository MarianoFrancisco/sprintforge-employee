package com.sprintforge.employee.position.application.port.in.command;

import com.sprintforge.employee.position.domain.Position;

public interface DeactivatePosition {
    Position handle(DeactivatePositionCommand command);
}
