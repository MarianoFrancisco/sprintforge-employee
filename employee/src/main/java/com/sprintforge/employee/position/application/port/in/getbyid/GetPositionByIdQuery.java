package com.sprintforge.employee.position.application.port.in.getbyid;

import java.util.UUID;

public record GetPositionByIdQuery(
        UUID id
) {
}
