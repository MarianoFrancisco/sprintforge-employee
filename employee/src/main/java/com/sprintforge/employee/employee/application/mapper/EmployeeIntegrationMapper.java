package com.sprintforge.employee.employee.application.mapper;

import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeReactivatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeRetiredIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeSuspendedIntegrationEvent;
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

    public EmployeeRetiredIntegrationEvent fromRetired(Employee employee) {
        return new EmployeeRetiredIntegrationEvent(
                employee.getId().value(),
                employee.getCui().value(),
                employee.getEmail().value(),
                employee.getFullName(),
                employee.getStatus().name()
        );
    }

    public EmployeeSuspendedIntegrationEvent fromSuspended(Employee employee) {
        return new EmployeeSuspendedIntegrationEvent(
                employee.getId().value(),
                employee.getCui().value(),
                employee.getEmail().value(),
                employee.getFullName(),
                employee.getStatus().name()
        );
    }

    public EmployeeReactivatedIntegrationEvent fromReactivated(Employee employee) {
        return new EmployeeReactivatedIntegrationEvent(
                employee.getId().value(),
                employee.getCui().value(),
                employee.getEmail().value(),
                employee.getFullName(),
                employee.getStatus().name()
        );
    }
}
