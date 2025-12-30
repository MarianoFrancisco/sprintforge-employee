package com.sprintforge.employee.payment.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.payment.application.port.in.GetAllPaymentsQuery;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.mapper.PaymentEntityMapper;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.repository.PaymentJpaRepository;
import com.sprintforge.employee.test.fixtures.EmployeeEntityFixture;
import com.sprintforge.employee.test.fixtures.EmployeeFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentRepositoryTest {

    @Mock
    private PaymentJpaRepository jpa;

    @InjectMocks
    private PaymentRepository repository;

    private PaymentEntity paymentEntity;
    private Employee employeeDomain;

    @BeforeEach
    void setUp() {
        employeeDomain = EmployeeFixture.validEmployee();

        EmployeeEntity employeeEntity = EmployeeEntityFixture.validEmployeeEntity(UUID.randomUUID());

        paymentEntity = PaymentEntity.builder()
                .id(UUID.randomUUID())
                .employee(employeeEntity)
                .date(LocalDate.of(2025, 1, 15))
                .baseSalary(BigDecimal.valueOf(5000))
                .bonus(BigDecimal.valueOf(100))
                .deduction(BigDecimal.valueOf(50))
                .total(BigDecimal.valueOf(5050))
                .notes("notes")
                .build();
    }

    @Test
    void shouldSavePayment() {
        Payment payment = mock(Payment.class);
        Payment mappedDomain = mock(Payment.class);

        when(jpa.save(eq(paymentEntity))).thenReturn(paymentEntity);

        try (MockedStatic<PaymentEntityMapper> mocked = mockStatic(PaymentEntityMapper.class)) {
            mocked.when(() -> PaymentEntityMapper.toEntity(payment)).thenReturn(paymentEntity);
            mocked.when(() -> PaymentEntityMapper.toDomain(paymentEntity)).thenReturn(mappedDomain);

            Payment saved = repository.save(payment);

            assertThat(saved).isSameAs(mappedDomain);
            verify(jpa).save(eq(paymentEntity));
            verifyNoMoreInteractions(jpa);
        }
    }

    @Test
    void shouldFindAllPayments() {
        GetAllPaymentsQuery query = mock(GetAllPaymentsQuery.class);
        when(query.employee()).thenReturn("john");
        when(query.position()).thenReturn("dev");
        when(query.fromDate()).thenReturn(LocalDate.of(2025, 1, 1));
        when(query.toDate()).thenReturn(LocalDate.of(2025, 1, 31));

        when(jpa.findAll(any(Specification.class))).thenReturn(List.of(paymentEntity));

        try (MockedStatic<PaymentEntityMapper> mocked = mockStatic(PaymentEntityMapper.class)) {
            Payment mapped = mock(Payment.class);
            mocked.when(() -> PaymentEntityMapper.toDomain(any(PaymentEntity.class))).thenReturn(mapped);

            List<Payment> result = repository.findAll(query);

            assertThat(result).hasSize(1);
            verify(jpa).findAll(any(Specification.class));
            verifyNoMoreInteractions(jpa);
        }
    }

    @Test
    void shouldFindLastPaymentByEmployee() {
        when(jpa.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(paymentEntity)));

        try (MockedStatic<PaymentEntityMapper> mocked = mockStatic(PaymentEntityMapper.class)) {
            Payment mapped = mock(Payment.class);
            mocked.when(() -> PaymentEntityMapper.toDomain(paymentEntity)).thenReturn(mapped);

            Optional<Payment> result = repository.findLastPaymentByEmployee(employeeDomain);

            assertThat(result).isPresent();
            verify(jpa).findAll(any(Specification.class), any(Pageable.class));
            verifyNoMoreInteractions(jpa);
        }
    }
}
