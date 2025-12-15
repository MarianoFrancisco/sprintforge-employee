package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record HireEmployeeRequestDTO(
        @NotBlank(message = "El CUI es obligatorio")
        @Pattern(
                regexp = "\\d{13}",
                message = "El CUI debe contener exactamente 13 dígitos numéricos"
        )
        String cui,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El correo electrónico no tiene un formato válido")
        @Size(max = 100, message = "El correo electrónico no debe superar los 100 caracteres")
        String email,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
        String firstName,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "El apellido no debe superar los 100 caracteres")
        String lastName,

        @NotBlank(message = "El número de teléfono es obligatorio")
        @Pattern(
                regexp = "\\d{8}",
                message = "El número de teléfono debe contener exactamente 8 dígitos numéricos"
        )
        String phoneNumber,

        @Past(message = "La fecha de nacimiento debe ser una fecha en el pasado")
        LocalDate birthDate,

        @NotNull(message = "El cargo es obligatorio")
        UUID positionId,

        @NotNull(message = "El tipo de jornada es obligatorio")
        EmployeeWorkloadType workloadType,

        @NotNull(message = "El salario es obligatorio")
        @Digits(integer = 8, fraction = 2, message = "El salario debe tener como máximo 8 enteros y 2 decimales")
        @DecimalMin(value = "0.00", message = "El salario no puede ser negativo")
        BigDecimal salary,

        @NotNull(message = "El porcentaje de IGSS es obligatorio")
        @Digits(integer = 3, fraction = 2, message = "El porcentaje de IGSS debe tener como máximo 3 enteros y 2 decimales")
        @DecimalMin(value = "0.00", message = "El porcentaje de IGSS no puede ser negativo")
        @DecimalMax(value = "100.00", message = "El porcentaje de IGSS no puede ser mayor a 100%")
        BigDecimal igssPercentage,

        @NotNull(message = "El porcentaje de IRTRA es obligatorio")
        @Digits(integer = 3, fraction = 2, message = "El porcentaje de IRTRA debe tener como máximo 3 enteros y 2 decimales")
        @DecimalMin(value = "0.00", message = "El porcentaje de IRTRA no puede ser negativo")
        @DecimalMax(value = "100.00", message = "El porcentaje de IRTRA no puede ser mayor a 100%")
        BigDecimal irtraPercentage,

        MultipartFile profileImage,

        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDate startDate,

        @Size(max = 255, message = "Las notas no deben superar los 255 caracteres")
        String notes
) {
}
