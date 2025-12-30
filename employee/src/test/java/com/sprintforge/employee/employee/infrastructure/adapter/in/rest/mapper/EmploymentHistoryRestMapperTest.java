package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeCui;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryId;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryNotes;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryPeriod;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmploymentHistoryResponseDTO;
import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.domain.valueobject.PositionId;
import com.sprintforge.employee.position.domain.valueobject.PositionName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmploymentHistoryRestMapperTest {

    @Test
    void shouldMapEmploymentHistoryToResponse() {
        Employee employee = mock(Employee.class);
        Position position = mock(Position.class);
        EmploymentHistory history = mock(EmploymentHistory.class);

        EmploymentHistoryPeriod period = mock(EmploymentHistoryPeriod.class);
        when(period.getStartDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(period.getEndDate()).thenReturn(null);

        Money salary = mock(Money.class);
        when(salary.amount()).thenReturn(new BigDecimal("5000.00"));

        EmploymentHistoryNotes notes = mock(EmploymentHistoryNotes.class);
        when(notes.value()).thenReturn("Promoted to Developer");

        UUID historyUuid = UUID.randomUUID();
        UUID positionUuid = UUID.randomUUID();

        when(history.getId()).thenReturn(new EmploymentHistoryId(historyUuid));
        when(history.getEmployee()).thenReturn(employee);
        when(history.getPeriod()).thenReturn(period);
        when(history.getSalary()).thenReturn(salary);
        when(history.getNotes()).thenReturn(notes);
        when(history.getType()).thenReturn(EmploymentHistoryType.HIRING);

        when(employee.getCui()).thenReturn(new EmployeeCui("1234567890101"));
        when(employee.getFullName()).thenReturn("John Doe");
        when(employee.getPosition()).thenReturn(position);

        when(position.getId()).thenReturn(new PositionId(positionUuid));
        when(position.getName()).thenReturn(new PositionName("Developer"));

        EmploymentHistoryResponseDTO dto = EmploymentHistoryRestMapper.toResponse(history);

        assertNotNull(dto);
        assertEquals("John Doe", dto.employeeFullname());
        assertEquals("1234567890101", dto.employeeCui());
        assertEquals(EmploymentHistoryType.HIRING, dto.type());
    }
}
