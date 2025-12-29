package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.common.infrastructure.adapter.in.rest.dto.EmployeesByEmploymentHistoryReportResponseDTO;
import com.sprintforge.employee.employee.application.port.in.query.GetHiringHistoryReport;
import com.sprintforge.employee.employee.application.port.in.query.GetHiringHistoryReportQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminationHistoryReport;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminationHistoryReportQuery;
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
public class InternalEmploymentHistoryController {

    private final GetHiringHistoryReport getHiringHistoryReport;
    private final GetTerminationHistoryReport getTerminationHistoryReport;

    @GetMapping("/hiring-report")
    public EmployeesByEmploymentHistoryReportResponseDTO getHiringHistoryReport(
            @Valid @ModelAttribute HiredEmployeeHistoryRequestDTO dto
    ) {
        GetHiringHistoryReportQuery query =
                InternalEmployeeHistoryRestMapper.toHiringHistoryReportQuery(dto);

        EmployeesByEmploymentHistoryReportResult result =
                getHiringHistoryReport.handle(query);

        return InternalEmployeeHistoryRestMapper.fromResult(result);
    }

    @GetMapping("/termination-report")
    public EmployeesByEmploymentHistoryReportResponseDTO getTerminationEmployeesReport(
            @Valid @ModelAttribute TerminatedEmployeeHistoryRequestDTO dto
    ) {
        GetTerminationHistoryReportQuery query =
                InternalEmployeeHistoryRestMapper.toTerminationHistoryReportQuery(dto);

        EmployeesByEmploymentHistoryReportResult result =
                getTerminationHistoryReport.handle(query);

        return InternalEmployeeHistoryRestMapper.fromResult(result);
    }
}
