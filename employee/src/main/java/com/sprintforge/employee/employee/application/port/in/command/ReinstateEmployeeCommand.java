package com.sprintforge.employee.employee.application.port.in.command;

import java.time.LocalDate;

public record ReinstateEmployeeCommand(
    String cui,
    LocalDate date,
    String notes
) {}
