package com.sprintforge.employee.payment.application.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sprintforge.employee.employee.domain.Employee;

public record PayEmployeeCommand(
    String cui,
    LocalDate date,
    Employee employee,
    BigDecimal bonus,
    BigDecimal deduction,
    String notes
) {}
