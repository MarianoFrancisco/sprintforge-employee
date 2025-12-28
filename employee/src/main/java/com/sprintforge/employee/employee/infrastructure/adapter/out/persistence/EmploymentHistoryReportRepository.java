package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.employee.application.port.in.query.GetHiredEmployeesReportQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminatedEmployeesReportQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.LoadEmploymentHistoryReport;
import com.sprintforge.employee.employee.application.port.result.EmployeeRow;
import com.sprintforge.employee.employee.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.projection.EmploymentHistoryEmployeeView;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository.EmploymentHistoryReportJpaRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@Repository
@RequiredArgsConstructor
public class EmploymentHistoryReportRepository
        implements LoadEmploymentHistoryReport {

    private final EmploymentHistoryReportJpaRepository jpa;

    @Override
    public EmployeesByEmploymentHistoryReportResult loadHired(GetHiredEmployeesReportQuery query) {
        List<EmploymentHistoryEmployeeView> rows =
                jpa.hiredEmployees(
                        query.from(),
                        query.to()
                );

        List<EmployeeRow> employees =
                mapToEmployeeRows(rows);

        return new EmployeesByEmploymentHistoryReportResult(
                employees.size(),
                employees
        );
    }

    @Override
    public EmployeesByEmploymentHistoryReportResult loadTerminated(GetTerminatedEmployeesReportQuery query) {
        List<EmploymentHistoryEmployeeView> rows =
                jpa.terminatedEmployees(
                        query.from(),
                        query.to()
                );

        List<EmployeeRow> employees =
                mapToEmployeeRows(rows);

        return new EmployeesByEmploymentHistoryReportResult(
                employees.size(),
                employees
        );
    }

    private List<EmployeeRow> mapToEmployeeRows(
            List<EmploymentHistoryEmployeeView> rows
    ) {
        List<EmployeeRow> employees = new ArrayList<>();

        for (EmploymentHistoryEmployeeView view : rows) {
            employees.add(new EmployeeRow(
                    view.getEmployeeId(),
                    view.getCui(),
                    view.getFullName(),
                    view.getDate()
            ));
        }

        return employees;
    }
}
