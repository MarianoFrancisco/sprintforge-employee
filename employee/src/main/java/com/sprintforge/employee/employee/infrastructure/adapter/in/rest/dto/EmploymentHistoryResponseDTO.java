package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;

public record EmploymentHistoryResponseDTO(
    UUID id,
    String employeeCui,
    String employeeFullname,
    UUID positionId,
    String positionName,
    EmploymentHistoryType type,
    LocalDate startDate,
    LocalDate endDate,
    BigDecimal salary,
    String notes
) {}
