package com.sprintforge.employee.employee.application.port.in.employee.hire;

import com.sprintforge.employee.employee.domain.Employee;

public interface HireEmployee {
    Employee handle(HireEmployeeCommand command);
}
