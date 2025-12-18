package com.sprintforge.employee.payment.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

public record PaymentResponseDTO(
    String cui,
    String fullName,
    UUID positionId,
    String positionName,
    String date,
    String baseSalary,
    String bonus,
    String deduction,
    String total,
    String notes
) {}
