package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateEmployeeDetailRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
        String firstName,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "El apellido no debe superar los 100 caracteres")
        String lastName,

        @NotBlank(message = "El número de teléfono es obligatorio")
        @Pattern(
                regexp = "\\d{8}",
                message = "El número de teléfono debe contener exactamente 8 dígitos"
        )
        String phoneNumber,

        @Past(message = "La fecha de nacimiento debe ser una fecha en el pasado")
        LocalDate birthDate,

        @NotNull(message = "El puesto es obligatorio")
        UUID positionId,

        @NotNull(message = "El porcentaje de IGSS es obligatorio")
        @Digits(integer = 3, fraction = 2, message = "El porcentaje de IGSS debe tener como máximo 3 enteros y 2 decimales")
        @DecimalMin(value = "0.00", message = "El porcentaje de IGSS no puede ser negativo")
        @DecimalMax(value = "100.00", message = "El porcentaje de IGSS no puede ser mayor a 100")
        BigDecimal igssPercentage,

        @NotNull(message = "El porcentaje de IRTRA es obligatorio")
        @Digits(integer = 3, fraction = 2, message = "El porcentaje de IRTRA debe tener como máximo 3 enteros y 2 decimales")
        @DecimalMin(value = "0.00", message = "El porcentaje de IRTRA no puede ser negativo")
        @DecimalMax(value = "100.00", message = "El porcentaje de IRTRA no puede ser mayor a 100")
        BigDecimal irtraPercentage,

        MultipartFile profileImage
) {
}
