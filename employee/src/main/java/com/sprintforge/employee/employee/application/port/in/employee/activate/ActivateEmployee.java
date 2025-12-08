package com.sprintforge.employee.employee.application.port.in.employee.activate;

import com.sprintforge.employee.employee.domain.Employee;

public interface ActivateEmployee {
    Employee handle(ActivateEmployeeCommand command);
}
