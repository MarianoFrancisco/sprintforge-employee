package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import com.sprintforge.employee.employee.domain.valueobject.EmployeePosition;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record EmployeeResponseDTO(
        UUID id,
        String cui,
        String email,

        String firstName,
        String lastName,
        String fullName,

        String phoneNumber,
        LocalDate birthDate,

        UUID positionId,
        EmployeeWorkloadType workloadType,

        BigDecimal salary,
        BigDecimal igssPercentage,
        BigDecimal irtraPercentage,

        String profileImage,

        boolean isActive,
        boolean isDeleted,

        long createdAt,
        long updatedAt,

        EmployeePosition position
) {
}
