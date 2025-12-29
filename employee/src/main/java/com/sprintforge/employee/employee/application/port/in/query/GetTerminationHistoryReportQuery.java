package com.sprintforge.employee.employee.application.port.in.query;

import java.time.LocalDate;

public record GetTerminationHistoryReportQuery(
        LocalDate from,
        LocalDate to
) {
}
