package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import java.util.List;

public record EmployeesByEmploymentHistoryReportResponseDTO(
        long total,
        List<EmployeeRowDTO> employees
) {
}
