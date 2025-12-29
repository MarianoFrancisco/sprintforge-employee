package com.sprintforge.employee.employee.application.port.in.query;

import java.time.LocalDate;

public record GetHiringHistoryReportQuery(
        LocalDate from,
        LocalDate to
) {
}
