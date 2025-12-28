package com.sprintforge.employee.payment.application.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayEmployeeCommand(
    String cui,
    LocalDate date,
    BigDecimal bonus,
    BigDecimal deduction,
    String notes
) {}
