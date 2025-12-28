package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record UpdateEmployeeDetailRequestDTO(
        @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
        String firstName,

        @Size(max = 100, message = "El apellido no debe superar los 100 caracteres")
        String lastName,

        @Pattern(
                regexp = "\\d{8}",
                message = "El número de teléfono debe contener exactamente 8 dígitos"
        )
        String phoneNumber,

        @Past(message = "La fecha de nacimiento debe ser una fecha en el pasado")
        LocalDate birthDate,

        MultipartFile profileImage
) {
}
