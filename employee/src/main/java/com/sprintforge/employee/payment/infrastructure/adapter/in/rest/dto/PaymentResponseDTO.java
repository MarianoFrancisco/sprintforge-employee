package com.sprintforge.employee.payment.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentResponseDTO(
    String cui,
    String fullName,
    UUID positionId,
    String positionName,
    LocalDate date,
    BigDecimal baseSalary,
    BigDecimal bonus,
    BigDecimal deduction,
    BigDecimal total,
    String notes
) {}
