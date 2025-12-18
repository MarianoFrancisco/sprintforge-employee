package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;

public record TerminateEmployeeRequestDTO(
    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    LocalDate date,

    @Size(max = 255, message = "Las notas no deben superar los 255 caracteres")
    String notes
) {}
