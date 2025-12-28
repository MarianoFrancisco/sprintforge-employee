package com.sprintforge.employee.employee.application.port.in.query;

import com.sprintforge.employee.employee.domain.Employee;

public interface GetEmployeeByCui {
    Employee handle(GetEmployeeByCuiQuery query);
}
