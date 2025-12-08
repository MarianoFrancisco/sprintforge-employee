package com.sprintforge.employee.employee.application.mapper;

import com.sprintforge.employee.employee.application.port.in.employee.hire.HireEmployeeCommand;
import com.sprintforge.employee.employee.domain.Employee;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeMapper {
    public Employee toDomain(HireEmployeeCommand command) {
        return new Employee(
                command.cui(),
                command.email(),
                command.firstName(),
                command.lastName(),
                command.phoneNumber(),
                command.birthDate(),
                command.positionId(),
                command.workloadType(),
                command.salary(),
                command.igssPercentage(),
                command.irtraPercentage(),
                null//command.profileImage()
        );
    }
}
