package com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper.EmployeeEntityMapper;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.employee.domain.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentEntityMapperTest {

    @Test
    void toDomain_returnsNull_whenEntityNull() {
        assertNull(PaymentEntityMapper.toDomain(null));
    }

    @Test
    void toEntity_returnsNull_whenDomainNull() {
        assertNull(PaymentEntityMapper.toEntity(null));
    }

    @Test
    void toDomain_mapsFields() {
        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.of(2025, 1, 10);

        EmployeeEntity employeeEntity = mock(EmployeeEntity.class);

        PaymentEntity entity = PaymentEntity.builder()
                .id(id)
                .employee(employeeEntity)
                .date(date)
                .baseSalary(new BigDecimal("1000"))
                .bonus(new BigDecimal("50"))
                .deduction(new BigDecimal("10"))
                .total(new BigDecimal("1040"))
                .notes("note")
                .build();

        Employee employeeDomain = mock(Employee.class);

        try (MockedStatic<EmployeeEntityMapper> mocked = mockStatic(EmployeeEntityMapper.class)) {
            mocked.when(() -> EmployeeEntityMapper.toDomain(employeeEntity)).thenReturn(employeeDomain);

            Payment payment = PaymentEntityMapper.toDomain(entity);

            assertNotNull(payment);
            assertEquals(id, payment.getId().value());
            assertEquals(date, payment.getDate().value());
            assertEquals(new BigDecimal("1000"), payment.getBaseSalary().amount());
            assertEquals(new BigDecimal("50"), payment.getBonus().amount());
            assertEquals(new BigDecimal("10"), payment.getDeduction().amount());
            assertEquals(new BigDecimal("1040"), payment.getTotal().amount());
            assertEquals("note", payment.getNotes().value());
        }
    }

    @Test
    void toEntity_mapsFields() {
        Payment payment = mock(Payment.class, RETURNS_DEEP_STUBS);
        Employee employee = mock(Employee.class);

        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.of(2025, 1, 10);

        when(payment.getId().value()).thenReturn(id);
        when(payment.getEmployee()).thenReturn(employee);
        when(payment.getDate().value()).thenReturn(date);
        when(payment.getBaseSalary().amount()).thenReturn(new BigDecimal("1000"));
        when(payment.getBonus().amount()).thenReturn(new BigDecimal("50"));
        when(payment.getDeduction().amount()).thenReturn(new BigDecimal("10"));
        when(payment.getTotal().amount()).thenReturn(new BigDecimal("1040"));
        when(payment.getNotes().value()).thenReturn("note");

        EmployeeEntity employeeEntity = mock(EmployeeEntity.class);

        try (MockedStatic<EmployeeEntityMapper> mocked = mockStatic(EmployeeEntityMapper.class)) {
            mocked.when(() -> EmployeeEntityMapper.toEntity(employee)).thenReturn(employeeEntity);

            PaymentEntity entity = PaymentEntityMapper.toEntity(payment);

            assertNotNull(entity);
            assertEquals(id, entity.getId());
            assertEquals(employeeEntity, entity.getEmployee());
            assertEquals(date, entity.getDate());
            assertEquals(new BigDecimal("1000"), entity.getBaseSalary());
            assertEquals(new BigDecimal("50"), entity.getBonus());
            assertEquals(new BigDecimal("10"), entity.getDeduction());
            assertEquals(new BigDecimal("1040"), entity.getTotal());
            assertEquals("note", entity.getNotes());
        }
    }
}
