package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import com.sprintforge.employee.employee.domain.valueobject.EmployeePosition;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;

import java.math.BigDecimal;
import java.time.LocalDate;
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

        EmployeeWorkloadType workloadType,

        BigDecimal salary,

        String profileImage,

        EmployeeStatus status,

        EmployeePosition position
) {
}
