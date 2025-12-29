package com.sprintforge.employee.employee.application.port.out.persistence;

import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.application.port.in.query.GetHiringHistoryReportQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminationHistoryReportQuery;

public interface LoadEmploymentHistoryReport {

    EmployeesByEmploymentHistoryReportResult loadHired(GetHiringHistoryReportQuery query);

    EmployeesByEmploymentHistoryReportResult loadTerminated(GetTerminationHistoryReportQuery query);
}
