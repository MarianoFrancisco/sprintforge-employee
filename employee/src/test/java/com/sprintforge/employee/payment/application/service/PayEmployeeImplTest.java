package com.sprintforge.employee.payment.application.service;

import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.exception.EmploymentHistoryNotFound;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeByCui;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmploymentByType;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.payment.application.exception.DuplicatePaymentException;
import com.sprintforge.employee.payment.application.exception.PaymentBeforeHiringException;
import com.sprintforge.employee.payment.application.exception.PaymentDateBeforeLastException;
import com.sprintforge.employee.payment.application.port.in.PayEmployeeCommand;
import com.sprintforge.employee.payment.application.port.out.event.PaymentEventPublisher;
import com.sprintforge.employee.payment.application.port.out.persistence.FindPayments;
import com.sprintforge.employee.payment.application.port.out.persistence.SavePayment;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.domain.valueobject.PaymentDate;
import com.sprintforge.employee.payment.domain.valueobject.PaymentNotes;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayEmployeeImplTest {

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

    private static final LocalDate DATE = LocalDate.of(2025, 1, 15);
    private static final BigDecimal SALARY = new BigDecimal("5000.00");

    @Mock
    private FindEmployeeByCui findEmployeeByCui;
    @Mock
    private FindPayments findPayments;
    @Mock
    private FindEmploymentByType findEmploymentByType;
    @Mock
    private SavePayment savePayment;
    @Mock
    private PaymentEventPublisher paymentEventPublisher;

    @InjectMocks
    private PayEmployeeImpl serviceToTest;

    @Test
    void shouldPayEmployeeSuccessfullyAndPublishEvent() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE, SALARY);
        EmploymentHistory hiring = EmploymentHistory.create(
                employee,
                EmploymentHistoryType.HIRING,
                LocalDate.of(2025, 1, 1),
                SALARY,
                "hire"
        );

        when(findEmployeeByCui.findByCui(CUI)).thenReturn(Optional.of(employee));
        when(findEmploymentByType.findByEmployeeAndType(employee, EmploymentHistoryType.HIRING))
                .thenReturn(Optional.of(hiring));

        when(findPayments.existsByEmployeeAndDate(employee, DATE)).thenReturn(false);
        when(findPayments.findLastPaymentByEmployee(employee)).thenReturn(Optional.empty());

        when(savePayment.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));

        Payment result = serviceToTest.handle(new PayEmployeeCommand(
                CUI,
                DATE,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                null
        ));

        assertNotNull(result);

        verify(savePayment).save(any(Payment.class));
        verify(paymentEventPublisher).publishPaidEmployee(any());
        verifyNoMoreInteractions(paymentEventPublisher);
    }

    @Test
    void shouldThrowEmployeeNotFound() {
        when(findEmployeeByCui.findByCui(CUI)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,
                () -> serviceToTest.handle(new PayEmployeeCommand(CUI, DATE, BigDecimal.ZERO, BigDecimal.ZERO, null)));

        verify(findEmployeeByCui).findByCui(CUI);
        verifyNoInteractions(findEmploymentByType, findPayments, savePayment, paymentEventPublisher);
    }

    @Test
    void shouldThrowEmploymentHistoryNotFound() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE, SALARY);

        when(findEmployeeByCui.findByCui(CUI)).thenReturn(Optional.of(employee));
        when(findEmploymentByType.findByEmployeeAndType(employee, EmploymentHistoryType.HIRING))
                .thenReturn(Optional.empty());

        assertThrows(EmploymentHistoryNotFound.class,
                () -> serviceToTest.handle(new PayEmployeeCommand(CUI, DATE, BigDecimal.ZERO, BigDecimal.ZERO, null)));

        verifyNoInteractions(savePayment, paymentEventPublisher);
    }

    @Test
    void shouldThrowDuplicatePaymentExceptionWhenAlreadyPaidThatDate() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE, SALARY);
        EmploymentHistory hiring = EmploymentHistory.create(
                employee,
                EmploymentHistoryType.HIRING,
                LocalDate.of(2025, 1, 1),
                SALARY,
                "hire"
        );

        when(findEmployeeByCui.findByCui(CUI)).thenReturn(Optional.of(employee));
        when(findEmploymentByType.findByEmployeeAndType(employee, EmploymentHistoryType.HIRING))
                .thenReturn(Optional.of(hiring));
        when(findPayments.existsByEmployeeAndDate(employee, DATE)).thenReturn(true);

        assertThrows(DuplicatePaymentException.class,
                () -> serviceToTest.handle(new PayEmployeeCommand(CUI, DATE, BigDecimal.ZERO, BigDecimal.ZERO, null)));

        verify(findPayments).existsByEmployeeAndDate(employee, DATE);
        verifyNoInteractions(savePayment, paymentEventPublisher);
    }

    @Test
    void shouldThrowPaymentDateBeforeLastExceptionWhenDateNotAfterLast() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE, SALARY);
        EmploymentHistory hiring = EmploymentHistory.create(
                employee,
                EmploymentHistoryType.HIRING,
                LocalDate.of(2025, 1, 1),
                SALARY,
                "hire"
        );

        Payment lastPayment = Payment.rehydrate(
                UUID.randomUUID(),
                employee,
                new PaymentDate(DATE),
                employee.getSalary(),
                new Money(BigDecimal.ZERO),
                new Money(BigDecimal.ZERO),
                employee.getSalary(),
                new PaymentNotes(null)
        );

        when(findEmployeeByCui.findByCui(CUI)).thenReturn(Optional.of(employee));
        when(findEmploymentByType.findByEmployeeAndType(employee, EmploymentHistoryType.HIRING))
                .thenReturn(Optional.of(hiring));
        when(findPayments.existsByEmployeeAndDate(employee, DATE)).thenReturn(false);
        when(findPayments.findLastPaymentByEmployee(employee)).thenReturn(Optional.of(lastPayment));

        assertThrows(PaymentDateBeforeLastException.class,
                () -> serviceToTest.handle(new PayEmployeeCommand(CUI, DATE, BigDecimal.ZERO, BigDecimal.ZERO, null)));

        verifyNoInteractions(savePayment, paymentEventPublisher);
    }

    @Test
    void shouldThrowPaymentBeforeHiringExceptionWhenFirstPaymentBeforeHiringStart() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE, SALARY);
        EmploymentHistory hiring = EmploymentHistory.create(
                employee,
                EmploymentHistoryType.HIRING,
                LocalDate.of(2025, 2, 1),
                SALARY,
                "hire"
        );

        when(findEmployeeByCui.findByCui(CUI)).thenReturn(Optional.of(employee));
        when(findEmploymentByType.findByEmployeeAndType(employee, EmploymentHistoryType.HIRING))
                .thenReturn(Optional.of(hiring));
        when(findPayments.existsByEmployeeAndDate(employee, DATE)).thenReturn(false);
        when(findPayments.findLastPaymentByEmployee(employee)).thenReturn(Optional.empty());

        assertThrows(PaymentBeforeHiringException.class,
                () -> serviceToTest.handle(new PayEmployeeCommand(CUI, DATE, BigDecimal.ZERO, BigDecimal.ZERO, null)));

        verifyNoInteractions(savePayment, paymentEventPublisher);
    }

    private static Employee buildEmployee(EmployeeStatus status, BigDecimal salary) {
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
                salary,
                null,
                status
        );
    }
}
