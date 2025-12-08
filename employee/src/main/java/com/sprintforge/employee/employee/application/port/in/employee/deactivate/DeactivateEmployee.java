package com.sprintforge.employee.employee.application.port.in.employee.deactivate;

import com.sprintforge.employee.employee.domain.Employee;

public interface DeactivateEmployee {
    Employee handle(DeactivateEmployeeCommand command);
}
