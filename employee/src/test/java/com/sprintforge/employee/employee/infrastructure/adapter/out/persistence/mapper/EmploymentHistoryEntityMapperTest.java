package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmploymentHistoryEntity;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.entity.PositionEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmploymentHistoryEntityMapperTest {

    private static final UUID HISTORY_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID EMPLOYEE_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
    private static final UUID POSITION_ID = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");

    @Test
    void shouldMapEntityToDomain() {
        EmployeeEntity employee = EmployeeEntity.builder()
                .id(EMPLOYEE_ID)
                .cui("1234567890101")
                .email("john@test.com")
                .firstName("John")
                .lastName("Doe")
                .fullName("John Doe")
                .phoneNumber("55556666")
                .birthDate(LocalDate.of(2000, 1, 1))
                .position(PositionEntity.builder().id(POSITION_ID).name("Dev").description("Dev").build())
                .salary(new BigDecimal("5000.00"))
                .build();

        EmploymentHistoryEntity entity = EmploymentHistoryEntity.builder()
                .id(HISTORY_ID)
                .employee(employee)
                .type(EmploymentHistoryType.HIRING)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(null)
                .salary(new BigDecimal("5000.00"))
                .notes("hire")
                .build();

        EmploymentHistory domain = EmploymentHistoryEntityMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(EmploymentHistoryType.HIRING, domain.getType());
        assertEquals(LocalDate.of(2025, 1, 1), domain.getPeriod().getStartDate());
        assertNull(domain.getPeriod().getEndDate());
        assertEquals(new BigDecimal("5000.00"), domain.getSalary().amount());
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        assertNull(EmploymentHistoryEntityMapper.toDomain(null));
        assertNull(EmploymentHistoryEntityMapper.toEntity(null));
    }
}
