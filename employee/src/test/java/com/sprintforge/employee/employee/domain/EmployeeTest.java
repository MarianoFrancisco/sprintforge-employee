package com.sprintforge.employee.employee.domain;

import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private static final UUID EMPLOYEE_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID POSITION_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

    private static final String CUI = "1234567890101";
    private static final String EMAIL = "john.doe@test.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String FULL_NAME = "John Doe";
    private static final String PHONE = "55556666";
    private static final LocalDate BIRTHDATE = LocalDate.now().minusYears(25);

    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");
    private static final BigDecimal SALARY = new BigDecimal("5000.00");

    @Test
    void shouldRehydrateEmployeeSuccessfully() {
        Position position = buildPosition();

        Employee employee = Employee.rehydrate(
                EMPLOYEE_ID,
                CUI,
                EMAIL,
                FIRST_NAME,
                LAST_NAME,
                FULL_NAME,
                PHONE,
                BIRTHDATE,
                position,
                EmployeeWorkloadType.FULL_TIME,
                SALARY,
                null,
                EmployeeStatus.ACTIVE
        );

        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals(EMPLOYEE_ID, employee.getId().value()),
                () -> assertEquals(CUI, employee.getCui().value()),
                () -> assertEquals(EMAIL, employee.getEmail().value()),
                () -> assertEquals(FIRST_NAME, employee.getFirstName().value()),
                () -> assertEquals(LAST_NAME, employee.getLastName().value()),
                () -> assertEquals(FULL_NAME, employee.getFullName()),
                () -> assertEquals(PHONE, employee.getPhoneNumber().value()),
                () -> assertEquals(BIRTHDATE, employee.getBirthDate().value()),
                () -> assertEquals(POSITION_ID, employee.getPosition().getId().value()),
                () -> assertEquals(EmployeeWorkloadType.FULL_TIME, employee.getWorkloadType()),
                () -> assertEquals(SALARY, employee.getSalary().amount()),
                () -> assertEquals(EmployeeStatus.ACTIVE, employee.getStatus()),
                () -> assertNotNull(employee.getProfileImage())
        );
    }

    private static Position buildPosition() {
        return new Position(
                POSITION_ID,
                "Developer",
                "Backend developer",
                true,
                false,
                NOW,
                NOW
        );
    }
}
