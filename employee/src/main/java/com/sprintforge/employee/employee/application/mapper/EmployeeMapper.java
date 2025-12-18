package com.sprintforge.employee.employee.application.mapper;

import com.sprintforge.employee.employee.application.port.in.command.HireEmployeeCommand;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.position.domain.Position;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeMapper {
    public Employee toDomain(HireEmployeeCommand command, Position position) {
        return Employee.hire(
                command.cui(),
                command.email(),
                command.firstName(),
                command.lastName(),
                command.phoneNumber(),
                command.birthDate(),
                position,
                command.workloadType(),
                command.salary(),
                null
        );
    }

    public EmployeeCreatedIntegrationEvent from(Employee employee) {
        return new EmployeeCreatedIntegrationEvent(
                employee.getId().value(),
                employee.getCui().value(),
                employee.getEmail().value(),
                employee.getFirstName().value(),
                employee.getLastName().value(),
                employee.getFullName()
        );
    }

}
