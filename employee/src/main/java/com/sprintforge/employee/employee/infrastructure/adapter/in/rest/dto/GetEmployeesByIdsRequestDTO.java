package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.UUID;

public record GetEmployeesByIdsRequestDTO(
        @NotEmpty(message = "La lista de identificadores no puede estar vacía")
        Set<UUID> ids
) {
}
