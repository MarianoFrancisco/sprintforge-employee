package com.sprintforge.employee.payment.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.payment.application.port.in.GetAllPaymentsQuery;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.repository.PaymentJpaRepository;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentRepositoryTest {

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

    @Mock
    private PaymentJpaRepository paymentJpaRepository;

    @InjectMocks
    private PaymentRepository repository;

    private PaymentEntity paymentEntity;
    private Payment domainPayment;
    private Employee domainEmployee;

    @BeforeEach
    void setUp() {
        Position position = new Position(
                POSITION_ID,
                "Developer",
                "Backend developer",
                true,
                false,
                NOW,
                NOW
        );

        domainEmployee = Employee.rehydrate(
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

        domainPayment = Payment.rehydrate(
                PAYMENT_ID,
                domainEmployee,
                new com.sprintforge.employee.payment.domain.valueobject.PaymentDate(PAYMENT_DATE),
                domainEmployee.getSalary(),
                new Money(new BigDecimal("100.00")),
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("5050.00")),
                new com.sprintforge.employee.payment.domain.valueobject.PaymentNotes("notes")
        );

        EmployeeEntity employeeEntity = mock(EmployeeEntity.class);

        paymentEntity = PaymentEntity.builder()
                .id(PAYMENT_ID)
                .employee(employeeEntity)
                .date(PAYMENT_DATE)
                .baseSalary(new BigDecimal("5000.00"))
                .bonus(new BigDecimal("100.00"))
                .deduction(new BigDecimal("50.00"))
                .total(new BigDecimal("5050.00"))
                .notes("notes")
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();
    }

    @Test
    void shouldSavePayment() {
        when(paymentJpaRepository.save(any(PaymentEntity.class))).thenReturn(paymentEntity);

        Payment saved = repository.save(domainPayment);

        assertThat(saved).isNotNull();
        verify(paymentJpaRepository).save(any(PaymentEntity.class));
        verifyNoMoreInteractions(paymentJpaRepository);
    }

    @Test
    void shouldFindAllPayments() {
        GetAllPaymentsQuery query = mock(GetAllPaymentsQuery.class);
        when(query.employee()).thenReturn("john");
        when(query.position()).thenReturn("dev");
        when(query.fromDate()).thenReturn(LocalDate.of(2025, 1, 1));
        when(query.toDate()).thenReturn(LocalDate.of(2025, 1, 31));

        when(paymentJpaRepository.findAll(any(Specification.class))).thenReturn(List.of(paymentEntity));

        List<Payment> result = repository.findAll(query);

        assertThat(result).hasSize(1);
        verify(paymentJpaRepository).findAll(any(Specification.class));
        verifyNoMoreInteractions(paymentJpaRepository);
    }

    @Test
    void shouldFindLastPaymentByEmployee() {
        when(paymentJpaRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn((Page) List.of(paymentEntity));

        var result = repository.findLastPaymentByEmployee(domainEmployee);

        assertThat(result).isPresent();

        verify(paymentJpaRepository).findAll(any(Specification.class), any(Pageable.class));
        verifyNoMoreInteractions(paymentJpaRepository);
    }

    @Test
    void shouldExistsByEmployeeAndDate() {
        when(paymentJpaRepository.existsByEmployeeAndDate(any(EmployeeEntity.class), eq(PAYMENT_DATE)))
                .thenReturn(true);

        boolean exists = repository.existsByEmployeeAndDate(domainEmployee, PAYMENT_DATE);

        assertThat(exists).isTrue();

        verify(paymentJpaRepository).existsByEmployeeAndDate(any(EmployeeEntity.class), eq(PAYMENT_DATE));
        verifyNoMoreInteractions(paymentJpaRepository);
    }
}
