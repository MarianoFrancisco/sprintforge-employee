package com.sprintforge.employee.employee.application.port.out.persistence;

import com.sprintforge.employee.employee.application.port.in.query.GetHiredEmployeesReportQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminatedEmployeesReportQuery;
import com.sprintforge.employee.employee.application.port.result.EmployeesByEmploymentHistoryReportResult;

public interface LoadEmploymentHistoryReport {

    EmployeesByEmploymentHistoryReportResult loadHired(GetHiredEmployeesReportQuery query);

    EmployeesByEmploymentHistoryReportResult loadTerminated(GetTerminatedEmployeesReportQuery query);
}
