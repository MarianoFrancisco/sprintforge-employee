package com.sprintforge.employee.employee.application.port.in.query;

public record GetAllEmployeesQuery(
        String searchTerm,
        Boolean isActive,
        Boolean isDeleted
) {
}
