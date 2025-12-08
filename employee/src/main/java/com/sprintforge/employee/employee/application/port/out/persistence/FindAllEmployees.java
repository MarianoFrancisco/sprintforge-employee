package com.sprintforge.employee.employee.application.port.out.persistence;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmployeesQuery;
import com.sprintforge.employee.employee.domain.Employee;

import java.util.List;

public interface FindAllEmployees {
    List<Employee> findAll(GetAllEmployeesQuery query);
}
