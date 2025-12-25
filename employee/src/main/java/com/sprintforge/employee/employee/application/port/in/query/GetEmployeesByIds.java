package com.sprintforge.employee.employee.application.port.in.query;

import com.sprintforge.employee.employee.domain.Employee;

import java.util.Set;

public interface GetEmployeesByIds {
    Set<Employee> handle(GetEmployeesByIdsQuery query);
}
