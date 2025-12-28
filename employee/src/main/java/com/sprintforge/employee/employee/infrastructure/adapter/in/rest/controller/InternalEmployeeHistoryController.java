package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.sprintforge.employee.employee.application.port.in.query.GetHiredEmployeesReport;
import com.sprintforge.employee.employee.application.port.in.query.GetHiredEmployeesReportQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminatedEmployeesReport;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminatedEmployeesReportQuery;
import com.sprintforge.employee.employee.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmployeesByEmploymentHistoryReportResponseDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.HiredEmployeeHistoryRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.TerminatedEmployeeHistoryRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper.InternalEmployeeHistoryRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal-api/v1/employee/history")
public class InternalEmployeeHistoryController {

    private final GetHiredEmployeesReport getHiredEmployeesReport;
    private final GetTerminatedEmployeesReport getTerminatedEmployeesReport;

    @GetMapping("/hired-report")
    public EmployeesByEmploymentHistoryReportResponseDTO getHiredEmployeesReport(
            @Valid @ModelAttribute HiredEmployeeHistoryRequestDTO dto
    ) {
        GetHiredEmployeesReportQuery query =
                InternalEmployeeHistoryRestMapper.toHiredEmployeesReportQuery(dto);

        EmployeesByEmploymentHistoryReportResult result =
                getHiredEmployeesReport.handle(query);

        return InternalEmployeeHistoryRestMapper.fromResult(result);
    }

    @GetMapping("/terminated-report")
    public EmployeesByEmploymentHistoryReportResponseDTO getTerminatedEmployeesReport(
            @Valid @ModelAttribute TerminatedEmployeeHistoryRequestDTO dto
    ) {
        GetTerminatedEmployeesReportQuery query =
                InternalEmployeeHistoryRestMapper.toTerminatedEmployeesReportQuery(dto);

        EmployeesByEmploymentHistoryReportResult result =
                getTerminatedEmployeesReport.handle(query);

        return InternalEmployeeHistoryRestMapper.fromResult(result);
    }
}
