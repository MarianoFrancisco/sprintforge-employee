package com.sprintforge.employee.position.application.port.in.query;

import com.sprintforge.employee.position.domain.Position;

import java.util.List;

public interface GetAllPositions {
    List<Position> handle(GetAllPositionsQuery query);
}
