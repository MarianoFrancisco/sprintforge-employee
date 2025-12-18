package com.sprintforge.employee.employee.application.port.out.persistence;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;

public interface FindActiveEmploymentByEmployee {
    EmploymentHistory findActiveByEmployee(Employee employee);
}
