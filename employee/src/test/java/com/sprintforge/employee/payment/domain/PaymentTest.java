package com.sprintforge.employee.payment.domain;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.payment.domain.valueobject.PaymentDate;
import com.sprintforge.employee.payment.domain.valueobject.PaymentNotes;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

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

    @Test
    void shouldCreatePaymentSuccessfully() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE, new BigDecimal("5000.00"));

        Payment payment = Payment.create(
                employee,
                new PaymentDate(LocalDate.now()),
                new Money(new BigDecimal("200.00")),
                new Money(new BigDecimal("100.00")),
                new PaymentNotes("ok")
        );

        assertAll(
                () -> assertNotNull(payment.getId()),
                () -> assertEquals(employee, payment.getEmployee()),
                () -> assertEquals(new BigDecimal("5000.00"), payment.getBaseSalary().amount()),
                () -> assertEquals(new BigDecimal("200.00"), payment.getBonus().amount()),
                () -> assertEquals(new BigDecimal("100.00"), payment.getDeduction().amount()),
                () -> assertEquals(new BigDecimal("5100.00"), payment.getTotal().amount())
        );
    }

    @Test
    void shouldThrowWhenEmployeeNotActive() {
        Employee employee = buildEmployee(EmployeeStatus.SUSPENDED, new BigDecimal("5000.00"));

        assertThrows(ValidationException.class, () -> Payment.create(
                employee,
                new PaymentDate(LocalDate.now()),
                new Money(BigDecimal.ZERO),
                new Money(BigDecimal.ZERO),
                new PaymentNotes(null)
        ));
    }

    @Test
    void shouldThrowWhenDeductionGreaterOrEqualSalaryPlusBonus() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE, new BigDecimal("1000.00"));

        assertThrows(ValidationException.class, () -> Payment.create(
                employee,
                new PaymentDate(LocalDate.now()),
                new Money(new BigDecimal("100.00")),
                new Money(new BigDecimal("1100.00")),
                new PaymentNotes(null)
        ));
    }

    private static Employee buildEmployee(EmployeeStatus status, BigDecimal salary) {
        Position position = buildPosition();

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
                salary,
                null,
                status
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
