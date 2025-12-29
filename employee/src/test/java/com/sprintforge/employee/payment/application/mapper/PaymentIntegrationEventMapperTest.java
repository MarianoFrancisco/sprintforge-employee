package com.sprintforge.employee.payment.application.mapper;

import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.payment.application.port.out.event.PaidEmployeeIntegrationEvent;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.domain.valueobject.PaymentDate;
import com.sprintforge.employee.payment.domain.valueobject.PaymentNotes;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentIntegrationEventMapperTest {

    private static final UUID PAYMENT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID EMPLOYEE_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID POSITION_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    private static final String CUI = "1234567890101";
    private static final String EMAIL = "john.doe@test.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String FULL_NAME = "John Doe";
    private static final String PHONE = "55556666";
    private static final LocalDate BIRTHDATE = LocalDate.now().minusYears(25);

    private static final LocalDate PAYMENT_DATE = LocalDate.of(2025, 1, 15);

    @Test
    void shouldMapPaymentToIntegrationEvent() {
        Payment payment = buildPayment();

        PaidEmployeeIntegrationEvent event = PaymentIntegrationEventMapper.from(payment);

        assertAll(
                () -> assertEquals(PAYMENT_ID, event.paymentId()),
                () -> assertEquals(EMPLOYEE_ID, event.employeeId()),
                () -> assertEquals(PAYMENT_DATE, event.date()),
                () -> assertEquals(new BigDecimal("5000.00"), event.baseSalary()),
                () -> assertEquals(new BigDecimal("100.00"), event.bonus()),
                () -> assertEquals(new BigDecimal("50.00"), event.deduction()),
                () -> assertEquals(new BigDecimal("5050.00"), event.total())
        );
    }

    private static Payment buildPayment() {
        Position position = new Position(
                POSITION_ID,
                "Developer",
                "Backend developer",
                true,
                false,
                NOW,
                NOW
        );

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
                new BigDecimal("5000.00"),
                null,
                EmployeeStatus.ACTIVE
        );

        return Payment.rehydrate(
                PAYMENT_ID,
                employee,
                new PaymentDate(PAYMENT_DATE),
                employee.getSalary(),
                new Money(new BigDecimal("100.00")),
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("5050.00")),
                new PaymentNotes("notes")
        );
    }
}
