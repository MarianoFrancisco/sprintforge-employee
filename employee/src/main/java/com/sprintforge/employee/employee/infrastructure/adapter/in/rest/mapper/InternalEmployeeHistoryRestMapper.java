package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.common.application.port.result.EmployeeRow;
import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.common.infrastructure.adapter.in.rest.dto.EmployeeRowDTO;
import com.sprintforge.common.infrastructure.adapter.in.rest.dto.EmployeesByEmploymentHistoryReportResponseDTO;
import com.sprintforge.employee.employee.application.port.in.query.GetHiringHistoryReportQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminationHistoryReportQuery;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.HiredEmployeeHistoryRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.TerminatedEmployeeHistoryRequestDTO;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class InternalEmployeeHistoryRestMapper {

    public GetHiringHistoryReportQuery toHiringHistoryReportQuery(
            HiredEmployeeHistoryRequestDTO dto
    ) {
        return new GetHiringHistoryReportQuery(
                dto.from(),
                dto.to()
        );
    }

    public GetTerminationHistoryReportQuery toTerminationHistoryReportQuery(
            TerminatedEmployeeHistoryRequestDTO dto
    ) {
        return new GetTerminationHistoryReportQuery(
                dto.from(),
                dto.to()
        );
    }

    public EmployeesByEmploymentHistoryReportResponseDTO fromResult(
            EmployeesByEmploymentHistoryReportResult result
    ) {
        return new EmployeesByEmploymentHistoryReportResponseDTO(
                result.from(),
                result.to(),
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
