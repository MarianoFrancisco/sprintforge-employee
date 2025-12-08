package com.sprintforge.employee.position.application.port.in.getall;

public record GetAllPositionsQuery(
        String searchTerm,
        Boolean isActive,
        Boolean isDeleted
) {
}
