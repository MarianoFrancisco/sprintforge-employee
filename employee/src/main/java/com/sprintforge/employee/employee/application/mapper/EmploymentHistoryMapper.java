package com.sprintforge.employee.employee.application.mapper;

import com.sprintforge.employee.employee.application.port.in.employee.hire.HireEmployeeCommand;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmploymentHistoryMapper {
    public EmploymentHistory toDomain(
            HireEmployeeCommand command,
            Employee employee
    ) {
        return new EmploymentHistory(
                employee.getId().value(),
                EmploymentHistoryType.HIRING,
                command.startDate(),
                null,
                command.salary(),
                command.notes()
        );
    }
}
