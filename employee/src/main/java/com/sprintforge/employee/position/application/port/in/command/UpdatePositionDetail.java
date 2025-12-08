package com.sprintforge.employee.position.application.port.in.command;

import com.sprintforge.employee.position.domain.Position;

public interface UpdatePositionDetail {
    Position handle(UpdatePositionDetailCommand command);
}
