package com.sprintforge.employee.payment.application.port.in;

import java.time.LocalDate;

public record GetAllPaymentsQuery(
    String employee,
    LocalDate fromDate,
    LocalDate toDate
) {}
