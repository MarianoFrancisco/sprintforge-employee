package com.sprintforge.employee.employee.application.port.out.persistence;

import com.sprintforge.employee.employee.domain.Employee;

import java.util.Optional;

public interface FindEmployeeByCui {
    Optional<Employee> findByCui(String cui);
}
