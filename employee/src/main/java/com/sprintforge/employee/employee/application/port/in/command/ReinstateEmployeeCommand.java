package com.sprintforge.employee.employee.application.port.in.command;

import java.time.LocalDate;
import java.util.UUID;

public record ReinstateEmployeeCommand(
    UUID employeeId,
    LocalDate date,
    String notes
) {}
