package com.sprintforge.employee.payment.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

public record PayEmployeeRequestDTO(
    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    LocalDate date,

    @DecimalMin(value = "0.0", inclusive = true, message = "El bono no puede ser negativo")
    BigDecimal bonus,

    @DecimalMin(value = "0.0", inclusive = true, message = "El descuento no puede ser negativo")
    BigDecimal deduction,

    @Size(max = 255, message = "Las notas no deben superar los 255 caracteres")
    String notes
) {}
