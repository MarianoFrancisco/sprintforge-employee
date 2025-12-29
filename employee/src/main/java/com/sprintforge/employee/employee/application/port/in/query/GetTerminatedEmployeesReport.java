package com.sprintforge.employee.employee.application.port.in.query;

import com.sprintforge.employee.employee.application.port.result.EmployeesByEmploymentHistoryReportResult;

public interface GetTerminatedEmployeesReport {
    EmployeesByEmploymentHistoryReportResult handle(GetTerminatedEmployeesReportQuery query);
}
