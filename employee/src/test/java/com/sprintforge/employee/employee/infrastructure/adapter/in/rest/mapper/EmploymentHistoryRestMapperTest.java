package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmploymentHistoryRestMapperTest {

    @Test
    void shouldMapEmploymentHistoryToResponse() {
        Employee employee = mock(Employee.class);
        EmploymentHistory history = mock(EmploymentHistory.class);

        when(history.getEmployee()).thenReturn(employee);
        when(employee.getFullName()).thenReturn("John Doe");
        when(history.getType()).thenReturn(EmploymentHistoryType.HIRING);

        var dto = EmploymentHistoryRestMapper.toResponse(history);

        assertNotNull(dto);
        assertEquals("John Doe", dto.employeeFullname());
        assertEquals(EmploymentHistoryType.HIRING, dto.type());
    }
}
