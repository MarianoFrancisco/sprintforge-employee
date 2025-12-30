package com.sprintforge.employee.test.fixtures;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.position.domain.Position;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public final class EmployeeFixture {

    public static Employee validEmployee() {
        return Employee.rehydrate(
                UUID.randomUUID(),
                "1234567890123",
                "john.doe@test.com",
                "John",
                "Doe",
                "John Doe",
                "55556666",
                LocalDate.of(1995, 1, 1),
                validPosition(),
                EmployeeWorkloadType.FULL_TIME,
                BigDecimal.valueOf(5000),
                null,
                EmployeeStatus.ACTIVE
        );
    }

    public static Employee validEmployee2() {
        return Employee.rehydrate(
                UUID.randomUUID(),
                "1234567890124",
                "john.doe@test2.com",
                "John",
                "Doe",
                "John Doe",
                "55556666",
                LocalDate.of(1995, 1, 1),
                validPosition(),
                EmployeeWorkloadType.FULL_TIME,
                BigDecimal.valueOf(5000),
                null,
                EmployeeStatus.ACTIVE
        );
    }

    public static Position validPosition() {
        Instant now = Instant.now();
        return new Position(
                UUID.randomUUID(),
                "Developer",
                "Developer",
                true,
                false,
                now,
                now
        );
    }

    private EmployeeFixture() {
    }
}
