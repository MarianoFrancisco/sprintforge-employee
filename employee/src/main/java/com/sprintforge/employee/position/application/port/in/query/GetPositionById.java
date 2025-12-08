package com.sprintforge.employee.position.application.port.in.query;

import com.sprintforge.employee.position.domain.Position;

public interface GetPositionById {
    Position handle(GetPositionByIdQuery query);
}
