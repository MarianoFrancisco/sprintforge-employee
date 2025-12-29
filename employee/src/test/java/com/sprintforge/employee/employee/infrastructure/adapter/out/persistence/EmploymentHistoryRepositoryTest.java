package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistoriesQuery;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmploymentHistoryEntity;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository.EmploymentHistoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmploymentHistoryRepositoryTest {

    @Mock
    private EmploymentHistoryJpaRepository jpa;

    @InjectMocks
    private EmploymentHistoryRepository repo;

    @Test
    void shouldFindAll() {
        when(jpa.findAll(any(Specification.class))).thenReturn(List.of(mock(EmploymentHistoryEntity.class)));

        List<EmploymentHistory> result = repo.findAll(new GetAllEmploymentHistoriesQuery(null, null, null, null, null));

        assertEquals(1, result.size());
        verify(jpa).findAll(any(Specification.class));
    }

    @Test
    void shouldFindByEmployeeAndType() {
        when(jpa.findByEmployeeAndType(any(), eq(EmploymentHistoryType.HIRING)))
                .thenReturn(List.of(mock(EmploymentHistoryEntity.class)));

        Optional<EmploymentHistory> result = repo.findByEmployeeAndType(mock(Employee.class), EmploymentHistoryType.HIRING);

        assertTrue(result.isPresent());
        verify(jpa).findByEmployeeAndType(any(), eq(EmploymentHistoryType.HIRING));
    }
}
