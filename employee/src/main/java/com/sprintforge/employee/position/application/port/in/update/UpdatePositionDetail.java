package com.sprintforge.employee.position.application.port.in.update;

import com.sprintforge.employee.position.domain.Position;

import java.util.UUID;

public interface UpdatePositionDetail {
    Position handle(UpdatePositionDetailCommand command);
}
