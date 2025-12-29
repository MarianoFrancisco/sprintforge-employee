package com.sprintforge.employee.employee.application.service;

import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.application.port.in.query.GetHiringHistoryReport;
import com.sprintforge.employee.employee.application.port.in.query.GetHiringHistoryReportQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.LoadEmploymentHistoryReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetHiringHistoryReportImpl implements GetHiringHistoryReport {

    private final LoadEmploymentHistoryReport loadEmploymentHistoryReport;

    @Override
    public EmployeesByEmploymentHistoryReportResult handle(GetHiringHistoryReportQuery query) {
        return loadEmploymentHistoryReport.loadHired(query);
    }
}
