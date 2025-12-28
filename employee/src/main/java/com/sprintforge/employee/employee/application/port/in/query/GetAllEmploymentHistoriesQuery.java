package com.sprintforge.employee.employee.application.port.in.query;

import java.time.LocalDate;

import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;

public record GetAllEmploymentHistoriesQuery(
    String employee,
    String position,
    EmploymentHistoryType type,
    LocalDate startDateFrom,
    LocalDate startDateTo
) {}
