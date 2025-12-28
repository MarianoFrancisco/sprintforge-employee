package com.sprintforge.employee.payment.infrastructure.adapter.out.messaging.kafka.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaidEmployeeKafkaMessage(
        UUID paymentId,
        UUID employeeId,
        LocalDate date,
        BigDecimal baseSalary,
        BigDecimal bonus,
        BigDecimal deduction,
        BigDecimal total
) {
}
