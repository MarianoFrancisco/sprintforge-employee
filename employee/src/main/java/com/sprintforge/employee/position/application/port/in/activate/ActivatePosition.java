package com.sprintforge.employee.position.application.port.in.activate;

import com.sprintforge.employee.position.domain.Position;

public interface ActivatePosition {
    Position handle(ActivatePositionCommand command);
}
