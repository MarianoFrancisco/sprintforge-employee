package com.sprintforge.employee.employee.application.port.out.persistence;

import com.sprintforge.employee.employee.domain.Employee;

public interface SaveEmployee {
    Employee save(Employee employee);
}
