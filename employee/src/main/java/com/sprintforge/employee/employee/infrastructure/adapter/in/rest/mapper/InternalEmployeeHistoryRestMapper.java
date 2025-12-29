package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.employee.application.port.in.query.GetHiredEmployeesReportQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminatedEmployeesReportQuery;
import com.sprintforge.employee.employee.application.port.result.EmployeeRow;
import com.sprintforge.employee.employee.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmployeeRowDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmployeesByEmploymentHistoryReportResponseDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.HiredEmployeeHistoryRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.TerminatedEmployeeHistoryRequestDTO;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class InternalEmployeeHistoryRestMapper {

    public GetHiredEmployeesReportQuery toHiredEmployeesReportQuery(
            HiredEmployeeHistoryRequestDTO dto
    ) {
        return new GetHiredEmployeesReportQuery(
                dto.from(),
                dto.to()
        );
    }

    public GetTerminatedEmployeesReportQuery toTerminatedEmployeesReportQuery(
            TerminatedEmployeeHistoryRequestDTO dto
    ) {
        return new GetTerminatedEmployeesReportQuery(
                dto.from(),
                dto.to()
        );
    }

    public EmployeesByEmploymentHistoryReportResponseDTO fromResult(
            EmployeesByEmploymentHistoryReportResult result
    ) {
        return new EmployeesByEmploymentHistoryReportResponseDTO(
                result.total(),
                mapEmployees(result.employees())
        );
    }

    private List<EmployeeRowDTO> mapEmployees(List<EmployeeRow> employees) {
        return employees.stream()
                .map(InternalEmployeeHistoryRestMapper::mapEmployee)
                .toList();
    }

    private EmployeeRowDTO mapEmployee(EmployeeRow employee) {
        return new EmployeeRowDTO(
                employee.employeeId(),
                employee.cui(),
                employee.fullName(),
                employee.eventDate()
        );
    }
}
