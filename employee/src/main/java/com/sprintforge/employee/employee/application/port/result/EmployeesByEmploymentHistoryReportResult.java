package com.sprintforge.employee.employee.application.port.result;

import java.util.List;

public record EmployeesByEmploymentHistoryReportResult(
        long total,
        List<EmployeeRow> employees
) {
}
