package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmploymentHistoryResponseDTO;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmploymentHistoryRestMapper {
    
    public EmploymentHistoryResponseDTO toResponse(EmploymentHistory employmentHistory) {
        Employee employee = employmentHistory.getEmployee();
        return new EmploymentHistoryResponseDTO(
            employmentHistory.getId().value(),
            employee.getCui().value(),
            employee.getFullName(),
            employee.getPosition().getId().value(),
            employee.getPosition().getName().value(),
            employmentHistory.getType(),
            employmentHistory.getPeriod().getStartDate(),
            employmentHistory.getPeriod().getEndDate(),
            employmentHistory.getSalary().amount(),
            employmentHistory.getNotes().value()
        );
    }
}
