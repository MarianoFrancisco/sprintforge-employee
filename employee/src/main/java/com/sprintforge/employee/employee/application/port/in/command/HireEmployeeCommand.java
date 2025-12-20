package com.sprintforge.employee.employee.application.port.in.command;

import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record HireEmployeeCommand(
        String cui,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate,
        UUID positionId,
        EmployeeWorkloadType workloadType,
        BigDecimal salary,
        Optional<ImageContent> profileImage,
        LocalDate startDate,
        String notes
) {
}
