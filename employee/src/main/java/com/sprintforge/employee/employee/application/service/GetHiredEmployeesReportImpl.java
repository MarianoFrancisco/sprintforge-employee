package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.port.in.query.GetHiredEmployeesReport;
import com.sprintforge.employee.employee.application.port.in.query.GetHiredEmployeesReportQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.LoadEmploymentHistoryReport;
import com.sprintforge.employee.employee.application.port.result.EmployeesByEmploymentHistoryReportResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetHiredEmployeesReportImpl implements GetHiredEmployeesReport {

    private final LoadEmploymentHistoryReport loadEmploymentHistoryReport;

    @Override
    public EmployeesByEmploymentHistoryReportResult handle(GetHiredEmployeesReportQuery query) {
        return loadEmploymentHistoryReport.loadHired(query);
    }
}
