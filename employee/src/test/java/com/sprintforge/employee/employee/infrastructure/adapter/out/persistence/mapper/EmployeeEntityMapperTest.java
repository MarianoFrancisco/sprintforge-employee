package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.entity.PositionEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeEntityMapperTest {

    private static final UUID EMPLOYEE_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID POSITION_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

    @Test
    void shouldMapEntityToDomain() {
        EmployeeEntity entity = EmployeeEntity.builder()
                .id(EMPLOYEE_ID)
                .cui("1234567890101")
                .email("john@test.com")
                .firstName("John")
                .lastName("Doe")
                .fullName("John Doe")
                .phoneNumber("55556666")
                .birthDate(LocalDate.of(2000, 1, 1))
                .position(PositionEntity.builder().id(POSITION_ID).name("Dev").description("Dev").build())
                .workloadType(EmployeeWorkloadType.FULL_TIME)
                .salary(new BigDecimal("5000.00"))
                .profileImage("key")
                .status(EmployeeStatus.ACTIVE)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Employee domain = EmployeeEntityMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals("1234567890101", domain.getCui().value());
        assertEquals("john@test.com", domain.getEmail().value());
        assertEquals("John", domain.getFirstName().value());
        assertEquals("Doe", domain.getLastName().value());
        assertEquals("John Doe", domain.getFullName());
        assertEquals(EmployeeStatus.ACTIVE, domain.getStatus());
        assertEquals(POSITION_ID, domain.getPosition().getId().value());
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        assertNull(EmployeeEntityMapper.toDomain(null));
        assertNull(EmployeeEntityMapper.toEntity(null));
    }
}
