package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmployeesQuery;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository.EmployeeJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryTest {

    @Mock
    private EmployeeJpaRepository jpa;

    @InjectMocks
    private EmployeeRepository repository;

    @Test
    void shouldExistsByCui() {
        when(jpa.existsByCui("123")).thenReturn(true);
        assertTrue(repository.existsByCui("123"));
        verify(jpa).existsByCui("123");
    }

    @Test
    void shouldExistsByEmail() {
        when(jpa.existsByEmail("a@b.com")).thenReturn(true);
        assertTrue(repository.existsByEmail("a@b.com"));
        verify(jpa).existsByEmail("a@b.com");
    }

    @Test
    void shouldFindByCui() {
        UUID id = UUID.randomUUID();
        when(jpa.findByCui("123")).thenReturn(Optional.of(EmployeeEntity.builder().id(id).cui("123").build()));

        Optional<Employee> result = repository.findByCui("123");

        assertTrue(result.isPresent());
        verify(jpa).findByCui("123");
    }

    @Test
    void shouldFindAll() {
        when(jpa.findAll(any(Specification.class))).thenReturn(List.of(EmployeeEntity.builder().id(UUID.randomUUID()).cui("123").build()));

        List<Employee> result = repository.findAll(new GetAllEmployeesQuery(null, null, null, null));

        assertEquals(1, result.size());
        verify(jpa).findAll(any(Specification.class));
    }

    @Test
    void shouldExistByIdIn() {
        Set<UUID> ids = Set.of(UUID.randomUUID(), UUID.randomUUID());
        when(jpa.existsByIdIn(ids)).thenReturn(true);

        assertTrue(repository.existByIdIn(ids));
        verify(jpa).existsByIdIn(ids);
    }

    @Test
    void shouldFindByIdIn() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        when(jpa.findByIdIn(Set.of(id1, id2)))
                .thenReturn(Set.of(EmployeeEntity.builder().id(id1).cui("1").build(), EmployeeEntity.builder().id(id2).cui("2").build()));

        Set<Employee> result = repository.findByIdIn(Set.of(id1, id2));

        assertEquals(2, result.size());
        verify(jpa).findByIdIn(Set.of(id1, id2));
    }
}
