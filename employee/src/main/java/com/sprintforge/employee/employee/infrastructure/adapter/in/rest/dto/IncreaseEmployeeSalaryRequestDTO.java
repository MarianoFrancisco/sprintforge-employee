package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

public record IncreaseEmployeeSalaryRequestDTO(
    @NotNull(message = "El monto de aumento es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto de aumento debe ser mayor que cero")
    BigDecimal increaseAmount,

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    LocalDate date,

    @Size(max = 255, message = "Las notas no deben superar los 255 caracteres")
    String notes
) {}
