package com.sprintforge.employee.payment.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.payment.application.port.in.PayEmployeeCommand;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.domain.valueobject.PaymentDate;
import com.sprintforge.employee.payment.domain.valueobject.PaymentNotes;
import com.sprintforge.employee.payment.infrastructure.adapter.in.rest.dto.PayEmployeeRequestDTO;
import com.sprintforge.employee.payment.infrastructure.adapter.in.rest.dto.PaymentResponseDTO;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRestMapperTest {

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
    void shouldMapToPayEmployeeCommand() {
        PayEmployeeRequestDTO dto = new PayEmployeeRequestDTO(
                LocalDate.of(2025, 1, 15),
                new BigDecimal("100.00"),
                new BigDecimal("50.00"),
                "notes"
        );

        PayEmployeeCommand cmd = PaymentRestMapper.toPayEmployeeCommand(CUI, dto);

        assertAll(
                () -> assertEquals(CUI, cmd.cui()),
                () -> assertEquals(dto.date(), cmd.date()),
                () -> assertEquals(dto.bonus(), cmd.bonus()),
                () -> assertEquals(dto.deduction(), cmd.deduction()),
                () -> assertEquals(dto.notes(), cmd.notes())
        );
    }

    @Test
    void shouldMapToResponseDTO() {
        Payment payment = buildPaymentForResponse();

        PaymentResponseDTO dto = PaymentRestMapper.toResponse(payment);

        assertAll(
                () -> assertEquals(CUI, dto.cui()),
                () -> assertEquals(FULL_NAME, dto.fullName()),
                () -> assertEquals(POSITION_ID, dto.positionId()),
                () -> assertEquals("Developer", dto.positionName()),
                () -> assertEquals(payment.getDate().value(), dto.date()),
                () -> assertEquals(payment.getBaseSalary().amount(), dto.baseSalary()),
                () -> assertEquals(payment.getBonus().amount(), dto.bonus()),
                () -> assertEquals(payment.getDeduction().amount(), dto.deduction()),
                () -> assertEquals(payment.getTotal().amount(), dto.total()),
                () -> assertEquals(payment.getNotes().value(), dto.notes())
        );
    }

    private static Payment buildPaymentForResponse() {
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
                UUID.randomUUID(),
                employee,
                new PaymentDate(LocalDate.of(2025, 1, 15)),
                employee.getSalary(),
                new Money(new BigDecimal("100.00")),
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("5050.00")),
                new PaymentNotes("notes")
        );
    }
}
