package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistoriesQuery;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeId;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmploymentHistoryEntity;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository.EmploymentHistoryJpaRepository;
import com.sprintforge.employee.test.fixtures.EmployeeEntityFixture;
import com.sprintforge.employee.test.fixtures.EmployeeFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmploymentHistoryRepositoryTest {

    @Mock
    private EmploymentHistoryJpaRepository jpa;

    @InjectMocks
    private EmploymentHistoryRepository repo;

    @Test
    void shouldFindAll() {
        EmploymentHistoryEntity entity = EmploymentHistoryEntity.builder()
                .id(UUID.randomUUID())
                .employee(EmployeeEntityFixture.validEmployeeEntity())
                .type(EmploymentHistoryType.HIRING)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(null)
                .salary(BigDecimal.valueOf(5000))
                .notes("notes")
                .build();

        when(jpa.findAll(any(Specification.class))).thenReturn(List.of(entity));

        List<EmploymentHistory> result = repo.findAll(new GetAllEmploymentHistoriesQuery(null, null, null, null, null));

        assertEquals(1, result.size());
        verify(jpa).findAll(any(Specification.class));
        verifyNoMoreInteractions(jpa);
    }

    @Test
    void shouldFindByEmployeeAndType() {
        Employee employee = EmployeeFixture.validEmployee();
        UUID employeeUuid = employee.getId().value();

        EmploymentHistoryEntity entity = EmploymentHistoryEntity.builder()
                .id(UUID.randomUUID())
                .employee(EmployeeEntityFixture.validEmployeeEntity(employeeUuid))
                .type(EmploymentHistoryType.HIRING)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(null)
                .salary(BigDecimal.valueOf(5000))
                .notes("notes")
                .build();

        when(jpa.findByEmployeeAndType(any(), eq(EmploymentHistoryType.HIRING)))
                .thenReturn(List.of(entity));

        Optional<EmploymentHistory> result = repo.findByEmployeeAndType(employee, EmploymentHistoryType.HIRING);

        assertTrue(result.isPresent());
        verify(jpa).findByEmployeeAndType(any(), eq(EmploymentHistoryType.HIRING));
        verifyNoMoreInteractions(jpa);
    }
}
