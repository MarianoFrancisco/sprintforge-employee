package com.sprintforge.employee.employee.application.port.in.command;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IncreaseEmployeeSalaryCommand(
    UUID employeeId,
    BigDecimal increaseAmount,
    LocalDate date,
    String notes
) {}
