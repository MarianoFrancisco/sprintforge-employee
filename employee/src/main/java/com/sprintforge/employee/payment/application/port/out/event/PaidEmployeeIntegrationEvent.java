package com.sprintforge.employee.payment.application.port.out.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaidEmployeeIntegrationEvent(
        UUID paymentId,
        UUID employeeId,
        LocalDate date,
        BigDecimal baseSalary,
        BigDecimal bonus,
        BigDecimal deduction,
        BigDecimal total
) {
}
