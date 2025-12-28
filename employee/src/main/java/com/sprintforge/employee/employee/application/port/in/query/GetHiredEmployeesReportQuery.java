package com.sprintforge.employee.employee.application.port.in.query;

import java.time.LocalDate;

public record GetHiredEmployeesReportQuery(
        LocalDate from,
        LocalDate to
) {
}
