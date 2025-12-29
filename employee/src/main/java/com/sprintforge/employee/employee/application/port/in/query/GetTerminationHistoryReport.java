package com.sprintforge.employee.employee.application.port.in.query;

import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;

public interface GetTerminationHistoryReport {
    EmployeesByEmploymentHistoryReportResult handle(GetTerminationHistoryReportQuery query);
}
