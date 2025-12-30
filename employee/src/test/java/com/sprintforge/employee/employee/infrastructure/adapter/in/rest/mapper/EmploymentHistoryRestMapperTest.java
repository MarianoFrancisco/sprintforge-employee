package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeCui;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryId;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.domain.valueobject.PositionId;
import com.sprintforge.employee.position.domain.valueobject.PositionName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmploymentHistoryRestMapperTest {

    @Test
    void shouldMapEmploymentHistoryToResponse() {
        Employee employee = mock(Employee.class);
        Position position = mock(Position.class);
        EmploymentHistory history = mock(EmploymentHistory.class);

        when(history.getId()).thenReturn(new EmploymentHistoryId(UUID.randomUUID()));
        when(history.getEmployee()).thenReturn(employee);
        when(employee.getCui()).thenReturn(new EmployeeCui("1234567890101"));
        when(employee.getFullName()).thenReturn("John Doe");
        when(employee.getPosition()).thenReturn(position);
        when(position.getId()).thenReturn(new PositionId(UUID.randomUUID()));
        when(position.getName()).thenReturn(new PositionName("Developer"));
        when(history.getType()).thenReturn(EmploymentHistoryType.HIRING);

        var dto = EmploymentHistoryRestMapper.toResponse(history);

        assertNotNull(dto);
        assertEquals("John Doe", dto.employeeFullname());
        assertEquals("1234567890101", dto.employeeCui());
        assertEquals(EmploymentHistoryType.HIRING, dto.type());
    }
}
