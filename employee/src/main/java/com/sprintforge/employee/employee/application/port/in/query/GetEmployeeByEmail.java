package com.sprintforge.employee.employee.application.port.in.query;

import com.sprintforge.employee.employee.domain.Employee;

public interface GetEmployeeByEmail {
    Employee handle(GetEmployeeByEmailQuery query);
}
