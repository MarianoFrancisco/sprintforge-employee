package com.sprintforge.employee.employee.application.port.in.query;

import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;

public record GetAllEmployeesQuery(
        String searchTerm,
        String position,
        EmployeeWorkloadType workloadType,
        EmployeeStatus status
) {
}
