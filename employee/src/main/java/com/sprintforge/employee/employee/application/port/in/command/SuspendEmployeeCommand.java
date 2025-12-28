package com.sprintforge.employee.employee.application.port.in.command;

import java.time.LocalDate;

public record SuspendEmployeeCommand(
    String cui,
    LocalDate date,
    String notes
) {}
