package com.sprintforge.employee.employee.application.mapper;

import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeCui;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeEmail;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeFirstName;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeLastName;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeIntegrationMapperTest {

    private static final UUID EMPLOYEE_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final String CUI = "1234567890101";
    private static final String EMAIL = "john.doe@test.com";

    @Test
    void shouldMapEmployeeCreatedEvent() {
        Employee employee = mock(Employee.class);

        when(employee.getId()).thenReturn(new EmployeeId(EMPLOYEE_ID));
        when(employee.getCui()).thenReturn(new EmployeeCui(CUI));
        when(employee.getEmail()).thenReturn(new EmployeeEmail(EMAIL));
        when(employee.getFirstName()).thenReturn(new EmployeeFirstName("John"));
        when(employee.getLastName()).thenReturn(new EmployeeLastName("Doe"));
        when(employee.getFullName()).thenReturn("John Doe");
        when(employee.getStatus()).thenReturn(EmployeeStatus.ACTIVE);

        EmployeeCreatedIntegrationEvent event = EmployeeIntegrationMapper.from(employee);

        assertAll(
                () -> assertEquals(EMPLOYEE_ID, event.employeeId()),
                () -> assertEquals(CUI, event.cui()),
                () -> assertEquals(EMAIL, event.email()),
                () -> assertEquals("John", event.firstName()),
                () -> assertEquals("Doe", event.lastName()),
                () -> assertEquals("John Doe", event.fullName()),
                () -> assertEquals("ACTIVE", event.status())
        );
    }
}
