package com.sprintforge.employee.position.application.port.out.persistence;

import com.sprintforge.employee.position.domain.Position;

public interface SavePosition {
    Position save(Position position);
}
