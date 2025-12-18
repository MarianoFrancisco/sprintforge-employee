package com.sprintforge.employee.employee.application.port.out.persistence;

import java.util.Optional;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;

public interface FindEmploymentByType {
    Optional<EmploymentHistory> findByEmployeeAndType(Employee employee, EmploymentHistoryType type);
}
