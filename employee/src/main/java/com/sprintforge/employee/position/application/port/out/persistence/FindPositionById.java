package com.sprintforge.employee.position.application.port.out.persistence;

import com.sprintforge.employee.position.domain.Position;

import java.util.Optional;
import java.util.UUID;

public interface FindPositionById {
    Optional<Position> findById(UUID id);
}
