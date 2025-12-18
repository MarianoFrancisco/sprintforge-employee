package com.sprintforge.employee.employee.application.port.in.command;

import com.sprintforge.employee.employee.domain.Employee;

public interface TerminateEmployee {
    Employee handle(TerminateEmployeeCommand command);
}
