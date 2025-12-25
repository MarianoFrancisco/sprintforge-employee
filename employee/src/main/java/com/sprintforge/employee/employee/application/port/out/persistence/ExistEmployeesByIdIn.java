package com.sprintforge.employee.employee.application.port.out.persistence;

import java.util.Set;
import java.util.UUID;

public interface ExistEmployeesByIdIn {
    boolean existByIdIn(Set<UUID> ids);
}
