package com.sprintforge.employee.employee.application.port.in.query;

import com.sprintforge.employee.employee.domain.Employee;

public interface GetEmployeeById {
    Employee handle(GetEmployeeByIdQuery query);
}
