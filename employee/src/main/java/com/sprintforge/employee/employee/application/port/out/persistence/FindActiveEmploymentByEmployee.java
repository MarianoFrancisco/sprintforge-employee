package com.sprintforge.employee.employee.application.port.out.persistence;

import java.util.Optional;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;

public interface FindActiveEmploymentByEmployee {
    Optional<EmploymentHistory> findActiveEmploymentPeriodByEmployee(Employee employee);
}
