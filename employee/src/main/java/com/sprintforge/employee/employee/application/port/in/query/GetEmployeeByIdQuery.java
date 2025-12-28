package com.sprintforge.employee.employee.application.port.in.query;

import java.util.UUID;

public record GetEmployeeByIdQuery(
        UUID id
) {
}
