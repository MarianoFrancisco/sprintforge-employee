package com.sprintforge.employee.employee.application.port.out.persistence;

import com.sprintforge.employee.employee.domain.Employee;

import java.util.Set;
import java.util.UUID;

public interface FindEmployeesByIdIn {
    Set<Employee> findByIdIn(Set<UUID> ids);
}
