package com.sprintforge.employee.employee.application.service;

import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminationHistoryReportQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.LoadEmploymentHistoryReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetTerminationHistoryReport implements com.sprintforge.employee.employee.application.port.in.query.GetTerminationHistoryReport {

    private final LoadEmploymentHistoryReport loadEmploymentHistoryReport;

    @Override
    public EmployeesByEmploymentHistoryReportResult handle(GetTerminationHistoryReportQuery query) {
        return loadEmploymentHistoryReport.loadTerminated(query);
    }
}
