package com.sprintforge.employee.employee.application.mapper;

import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeReactivatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeRetiredIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeSuspendedIntegrationEvent;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeCui;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeEmail;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeFirstName;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeId;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeLastName;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeIntegrationMapperTest {

    @Test
    void shouldMapCreatedEventFromEmployee() {
        Employee employee = mock(Employee.class);

        UUID id = UUID.randomUUID();
        when(employee.getId()).thenReturn(new EmployeeId(id));
        when(employee.getCui()).thenReturn(new EmployeeCui("1234567890101"));
        when(employee.getEmail()).thenReturn(new EmployeeEmail("john@test.com"));
        when(employee.getFirstName()).thenReturn(new EmployeeFirstName("John"));
        when(employee.getLastName()).thenReturn(new EmployeeLastName("Doe"));
        when(employee.getFullName()).thenReturn("John Doe");
        when(employee.getStatus()).thenReturn(EmployeeStatus.ACTIVE);

        EmployeeCreatedIntegrationEvent event = EmployeeIntegrationMapper.from(employee);

        assertEquals(id, event.employeeId());
        assertEquals("1234567890101", event.cui());
        assertEquals("john@test.com", event.email());
        assertEquals("John", event.firstName());
        assertEquals("Doe", event.lastName());
        assertEquals("John Doe", event.fullName());
        assertEquals("ACTIVE", event.status());
    }

    @Test
    void shouldMapRetiredEvent() {
        Employee employee = mock(Employee.class);

        UUID id = UUID.randomUUID();
        when(employee.getId()).thenReturn(new EmployeeId(id));
        when(employee.getCui()).thenReturn(new EmployeeCui("1234567890101"));
        when(employee.getEmail()).thenReturn(new EmployeeEmail("john@test.com"));
        when(employee.getFullName()).thenReturn("John Doe");
        when(employee.getStatus()).thenReturn(EmployeeStatus.TERMINATED);

        EmployeeRetiredIntegrationEvent event = EmployeeIntegrationMapper.fromRetired(employee);

        assertEquals(id, event.employeeId());
        assertEquals("1234567890101", event.cui());
        assertEquals("john@test.com", event.email());
        assertEquals("John Doe", event.fullName());
        assertEquals("TERMINATED", event.status());
    }

    @Test
    void shouldMapSuspendedEvent() {
        Employee employee = mock(Employee.class);

        UUID id = UUID.randomUUID();
        when(employee.getId()).thenReturn(new EmployeeId(id));
        when(employee.getCui()).thenReturn(new EmployeeCui("1234567890101"));
        when(employee.getEmail()).thenReturn(new EmployeeEmail("john@test.com"));
        when(employee.getFullName()).thenReturn("John Doe");
        when(employee.getStatus()).thenReturn(EmployeeStatus.SUSPENDED);

        EmployeeSuspendedIntegrationEvent event = EmployeeIntegrationMapper.fromSuspended(employee);

        assertEquals(id, event.employeeId());
        assertEquals("1234567890101", event.cui());
        assertEquals("john@test.com", event.email());
        assertEquals("John Doe", event.fullName());
        assertEquals("SUSPENDED", event.status());
    }

    @Test
    void shouldMapReactivatedEvent() {
        Employee employee = mock(Employee.class);

        UUID id = UUID.randomUUID();
        when(employee.getId()).thenReturn(new EmployeeId(id));
        when(employee.getCui()).thenReturn(new EmployeeCui("1234567890101"));
        when(employee.getEmail()).thenReturn(new EmployeeEmail("john@test.com"));
        when(employee.getFullName()).thenReturn("John Doe");
        when(employee.getStatus()).thenReturn(EmployeeStatus.ACTIVE);

        EmployeeReactivatedIntegrationEvent event = EmployeeIntegrationMapper.fromReactivated(employee);

        assertEquals(id, event.employeeId());
        assertEquals("1234567890101", event.cui());
        assertEquals("john@test.com", event.email());
        assertEquals("John Doe", event.fullName());
        assertEquals("ACTIVE", event.status());
    }
}
