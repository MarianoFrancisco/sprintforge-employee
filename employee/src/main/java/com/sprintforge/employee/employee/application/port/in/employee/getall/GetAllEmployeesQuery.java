package com.sprintforge.employee.employee.application.port.in.employee.getall;

public record GetAllEmployeesQuery(
        String searchTerm,
        Boolean isActive,
        Boolean isDeleted
) {
}
