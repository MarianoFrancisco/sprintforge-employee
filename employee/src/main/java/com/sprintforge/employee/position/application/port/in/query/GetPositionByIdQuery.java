package com.sprintforge.employee.position.application.port.in.query;

import java.util.UUID;

public record GetPositionByIdQuery(
        UUID id
) {
}
