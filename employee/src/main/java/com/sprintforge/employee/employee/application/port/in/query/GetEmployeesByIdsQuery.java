package com.sprintforge.employee.employee.application.port.in.query;

import java.util.Set;
import java.util.UUID;

public record GetEmployeesByIdsQuery(
        Set<UUID> ids
) {
}
