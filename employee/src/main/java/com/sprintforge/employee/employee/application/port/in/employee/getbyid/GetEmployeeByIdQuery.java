package com.sprintforge.employee.employee.application.port.in.employee.getbyid;

import java.util.UUID;

public record GetEmployeeByIdQuery(
        UUID id
) {
}
