package com.sprintforge.employee.employee.application.port.in.command;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateEmployeeDetailCommand(
        UUID id,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate,
        MultipartFile profileImage
) {
}
