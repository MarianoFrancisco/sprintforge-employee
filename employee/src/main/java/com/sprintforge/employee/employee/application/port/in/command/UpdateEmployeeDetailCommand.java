package com.sprintforge.employee.employee.application.port.in.command;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record UpdateEmployeeDetailCommand(
        UUID id,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate,
        Optional<ImageContent> profileImage
) {
}
