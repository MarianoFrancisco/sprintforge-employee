package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmployeesQuery;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository.EmployeeJpaRepository;
import com.sprintforge.employee.test.fixtures.EmployeeEntityFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryTest {

    @Mock
    private EmployeeJpaRepository jpa;

    @InjectMocks
    private EmployeeRepository repository;

    @Test
    void shouldExistsByCui() {
        when(jpa.existsByCui("1234567890123")).thenReturn(true);

        boolean exists = repository.existsByCui("1234567890123");

        assertTrue(exists);
        verify(jpa).existsByCui("1234567890123");
        verifyNoMoreInteractions(jpa);
    }

    @Test
    void shouldExistsByEmail() {
        when(jpa.existsByEmail("john.doe@test.com")).thenReturn(true);

        boolean exists = repository.existsByEmail("john.doe@test.com");

        assertTrue(exists);
        verify(jpa).existsByEmail("john.doe@test.com");
        verifyNoMoreInteractions(jpa);
    }

    @Test
    void shouldFindByCui() {
        EmployeeEntity entity = EmployeeEntityFixture.validEmployeeEntity();
        when(jpa.findByCui("1234567890123")).thenReturn(Optional.of(entity));

        Optional<Employee> result = repository.findByCui("1234567890123");

        assertTrue(result.isPresent());
        verify(jpa).findByCui("1234567890123");
        verifyNoMoreInteractions(jpa);
    }

    @Test
    void shouldFindAll() {
        when(jpa.findAll(any(Specification.class)))
                .thenReturn(List.of(EmployeeEntityFixture.validEmployeeEntity()));

        List<Employee> result = repository.findAll(new GetAllEmployeesQuery(null, null, null, null));

        assertEquals(1, result.size());
        verify(jpa).findAll(any(Specification.class));
        verifyNoMoreInteractions(jpa);
    }

    @Test
    void shouldExistByIdIn() {
        Set<UUID> ids = Set.of(UUID.randomUUID(), UUID.randomUUID());
        when(jpa.existsByIdIn(ids)).thenReturn(true);

        boolean exists = repository.existByIdIn(ids);

        assertTrue(exists);
        verify(jpa).existsByIdIn(ids);
        verifyNoMoreInteractions(jpa);
    }

    @Test
    void shouldFindByIdIn() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        EmployeeEntity e1 = EmployeeEntityFixture.validEmployeeEntity(id1, "1234567890123", "john.doe@test.com");
        EmployeeEntity e2 = EmployeeEntityFixture.validEmployeeEntity(id2, "9876543210987", "jane.doe@test.com");

        when(jpa.findByIdIn(anySet())).thenReturn(Set.of(e1, e2));

        Set<Employee> result = repository.findByIdIn(Set.of(id1, id2));

        assertEquals(2, result.size());
        verify(jpa).findByIdIn(anySet());
        verifyNoMoreInteractions(jpa);
    }
}
