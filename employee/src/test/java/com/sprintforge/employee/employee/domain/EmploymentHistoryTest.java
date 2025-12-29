package com.sprintforge.employee.employee.domain;

import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmploymentHistoryTest {

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
    void shouldCreateHiringHistorySuccessfully() {
        Employee employee = buildEmployee();

        LocalDate startDate = LocalDate.now().minusDays(5);

        EmploymentHistory history = EmploymentHistory.create(
                employee,
                EmploymentHistoryType.HIRING,
                startDate,
                SALARY,
                "hire"
        );

        assertAll(
                () -> assertNotNull(history.getId()),
                () -> assertEquals(employee, history.getEmployee()),
                () -> assertEquals(EmploymentHistoryType.HIRING, history.getType()),
                () -> assertEquals(startDate, history.getPeriod().getStartDate()),
                () -> assertNull(history.getPeriod().getEndDate()),
                () -> assertTrue(history.isActive())
        );
    }

    @Test
    void shouldCloseHistory() {
        Employee employee = buildEmployee();

        LocalDate startDate = LocalDate.now().minusDays(10);
        EmploymentHistory history = EmploymentHistory.create(
                employee,
                EmploymentHistoryType.SUSPENSION,
                startDate,
                SALARY,
                "susp"
        );

        LocalDate endDate = LocalDate.now().minusDays(1);
        history.close(endDate);

        assertAll(
                () -> assertEquals(endDate, history.getPeriod().getEndDate()),
                () -> assertFalse(history.isActive())
        );
    }

    private static Employee buildEmployee() {
        Position position = new Position(
                POSITION_ID,
                "Developer",
                "Backend developer",
                true,
                false,
                NOW,
                NOW
        );

        return Employee.rehydrate(
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
    }
}
