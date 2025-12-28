package com.sprintforge.employee.employee.application.port.in.command;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncreaseEmployeeSalaryCommand(
    String cui,
    BigDecimal increaseAmount,
    LocalDate date,
    String notes
) {}
