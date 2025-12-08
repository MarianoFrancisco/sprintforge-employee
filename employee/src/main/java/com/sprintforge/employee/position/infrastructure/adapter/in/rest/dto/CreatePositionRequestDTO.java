package com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePositionRequestDTO(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
        String name,

        @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
        String description
) {
}
