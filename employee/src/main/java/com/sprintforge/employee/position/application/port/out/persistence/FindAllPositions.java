package com.sprintforge.employee.position.application.port.out.persistence;

import com.sprintforge.employee.position.application.port.in.query.GetAllPositionsQuery;
import com.sprintforge.employee.position.domain.Position;

import java.util.List;

public interface FindAllPositions {
    List<Position> findAll(GetAllPositionsQuery query);
}
