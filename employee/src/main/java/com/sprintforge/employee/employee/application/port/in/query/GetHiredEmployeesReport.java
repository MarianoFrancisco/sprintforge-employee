package com.sprintforge.employee.employee.application.port.in.query;

import com.sprintforge.employee.employee.application.port.result.EmployeesByEmploymentHistoryReportResult;

public interface GetHiredEmployeesReport {
    EmployeesByEmploymentHistoryReportResult handle(GetHiredEmployeesReportQuery query);
}
