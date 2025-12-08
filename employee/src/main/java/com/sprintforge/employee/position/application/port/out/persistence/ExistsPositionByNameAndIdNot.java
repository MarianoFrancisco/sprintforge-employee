package com.sprintforge.employee.position.application.port.out.persistence;

import java.util.UUID;

public interface ExistsPositionByNameAndIdNot {
    boolean existsByNameAndIdNot(String name, UUID id);
}
