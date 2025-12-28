package com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto;

import java.time.Instant;
import java.util.UUID;

public record PositionResponseDTO(
        UUID id,
        String name,
        String description,
        boolean isActive,
        boolean isDeleted,
        Instant createdAt,
        Instant updatedAt
) {
}
