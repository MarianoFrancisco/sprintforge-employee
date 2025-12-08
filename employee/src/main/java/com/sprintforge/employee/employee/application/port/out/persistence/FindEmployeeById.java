package com.sprintforge.employee.employee.application.port.out.persistence;

import com.sprintforge.employee.employee.domain.Employee;

import java.util.Optional;
import java.util.UUID;

public interface FindEmployeeById {
    Optional<Employee> findById(UUID id);
}
