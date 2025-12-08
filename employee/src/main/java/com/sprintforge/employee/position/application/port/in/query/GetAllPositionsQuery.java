package com.sprintforge.employee.position.application.port.in.query;

public record GetAllPositionsQuery(
        String searchTerm,
        Boolean isActive,
        Boolean isDeleted
) {
}
