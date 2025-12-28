package com.sprintforge.employee.employee.application.mapper;

import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.employee.domain.Employee;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeIntegrationMapper {
    public EmployeeCreatedIntegrationEvent from(Employee employee) {
        return new EmployeeCreatedIntegrationEvent(
                employee.getId().value(),
                employee.getCui().value(),
                employee.getEmail().value(),
                employee.getFirstName().value(),
                employee.getLastName().value(),
                employee.getFullName(),
                employee.getStatus().name()
        );
    }
}
