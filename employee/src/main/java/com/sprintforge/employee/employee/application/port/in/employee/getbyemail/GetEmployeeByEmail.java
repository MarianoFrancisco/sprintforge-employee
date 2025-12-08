package com.sprintforge.employee.employee.application.port.in.employee.getbyemail;

import com.sprintforge.employee.employee.domain.Employee;

public interface GetEmployeeByEmail {
    Employee handle(GetEmployeeByEmailQuery query);
}
