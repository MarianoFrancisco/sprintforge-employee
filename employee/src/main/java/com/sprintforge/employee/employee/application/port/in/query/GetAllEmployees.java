package com.sprintforge.employee.employee.application.port.in.query;

import com.sprintforge.employee.employee.domain.Employee;

import java.util.List;

public interface GetAllEmployees {
    List<Employee> handle(GetAllEmployeesQuery query);
}
