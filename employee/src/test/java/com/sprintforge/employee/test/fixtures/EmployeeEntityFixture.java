package com.sprintforge.employee.test.fixtures;

import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.entity.PositionEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class EmployeeEntityFixture {
    public static EmployeeEntity validEmployeeEntity() {
        return EmployeeEntity.builder()
                .id(UUID.randomUUID())
                .cui("1234567890123")
                .email("john.doe@test.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("55556666")
                .birthDate(LocalDate.of(1995, 1, 1))
                .salary(BigDecimal.valueOf(5000))
                .workloadType(EmployeeWorkloadType.FULL_TIME)
                .status(EmployeeStatus.ACTIVE)
                .position(validPositionEntity())
                .build();
    }

    public static EmployeeEntity validEmployeeEntity(UUID id) {
        return EmployeeEntity.builder()
                .id(id)
                .cui("1234567890123")
                .email("john.doe@test.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("55556666")
                .birthDate(LocalDate.of(1995, 1, 1))
                .salary(BigDecimal.valueOf(5000))
                .workloadType(EmployeeWorkloadType.FULL_TIME)
                .status(EmployeeStatus.ACTIVE)
                .position(validPositionEntity())
                .build();
    }

    public static EmployeeEntity validEmployeeEntity(UUID id, String cui, String email) {
        return EmployeeEntity.builder()
                .id(id)
                .cui(cui)
                .email(email)
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("55556666")
                .birthDate(LocalDate.of(1995, 1, 1))
                .salary(BigDecimal.valueOf(5000))
                .workloadType(EmployeeWorkloadType.FULL_TIME)
                .status(EmployeeStatus.ACTIVE)
                .position(validPositionEntity())
                .build();
    }

    private static PositionEntity validPositionEntity() {
        return PositionEntity.builder()
                .id(UUID.randomUUID())
                .name("Developer")
                .description("Developer")
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    private EmployeeEntityFixture() {
    }
}
