package com.sprintforge.employee.employee.application.port.in.query;

import java.time.LocalDate;

public record GetTerminatedEmployeesReportQuery(
        LocalDate from,
        LocalDate to
) {
}
