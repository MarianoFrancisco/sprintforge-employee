package com.sprintforge.employee.employee.application.port.in.employee.update;

import com.sprintforge.employee.employee.domain.Employee;

public interface UpdateEmployeeDetail {
    Employee handle(UpdateEmployeeDetailCommand command);
}
