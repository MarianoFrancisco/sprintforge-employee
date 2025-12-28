package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.port.in.query.GetTerminatedEmployeesReport;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminatedEmployeesReportQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.LoadEmploymentHistoryReport;
import com.sprintforge.employee.employee.application.port.result.EmployeesByEmploymentHistoryReportResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetTerminatedEmployeesReportImpl implements GetTerminatedEmployeesReport {

    private final LoadEmploymentHistoryReport loadEmploymentHistoryReport;

    @Override
    public EmployeesByEmploymentHistoryReportResult handle(GetTerminatedEmployeesReportQuery query) {
        return loadEmploymentHistoryReport.loadTerminated(query);
    }
}
