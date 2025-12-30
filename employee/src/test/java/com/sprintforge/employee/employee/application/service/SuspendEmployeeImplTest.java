package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmploymentPeriodOverlapException;
import com.sprintforge.employee.employee.application.mapper.EmployeeIntegrationMapper;
import com.sprintforge.employee.employee.application.port.in.command.SuspendEmployeeCommand;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeEventPublisher;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeSuspendedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.persistence.FindActiveEmploymentByEmployee;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeByCui;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmployee;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmploymentHistory;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryPeriod;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SuspendEmployeeImplTest {

    @Test
    void handle_happyPath_suspends_saves_history_publishesEvent() {
        Employee employee = mock(Employee.class, RETURNS_DEEP_STUBS);
        EmploymentHistory active = mock(EmploymentHistory.class);
        EmploymentHistoryPeriod period = mock(EmploymentHistoryPeriod.class);

        when(active.getPeriod()).thenReturn(period);
        when(period.getStartDate()).thenReturn(LocalDate.of(2025, 1, 1));
        when(employee.getSalary().amount()).thenReturn(new BigDecimal("2000"));

        FindEmployeeByCui findEmployeeByCui = mock(FindEmployeeByCui.class);
        when(findEmployeeByCui.findByCui("cui")).thenReturn(Optional.of(employee));

        FindActiveEmploymentByEmployee findActive = mock(FindActiveEmploymentByEmployee.class);
        when(findActive.findActiveEmploymentPeriodByEmployee(employee)).thenReturn(Optional.of(active));

        SaveEmployee saveEmployee = mock(SaveEmployee.class);
        when(saveEmployee.save(employee)).thenReturn(employee);

        SaveEmploymentHistory saveHistory = mock(SaveEmploymentHistory.class);
        EmployeeEventPublisher publisher = mock(EmployeeEventPublisher.class);

        SuspendEmployeeImpl service = new SuspendEmployeeImpl(
                findEmployeeByCui, findActive, saveEmployee, saveHistory, publisher
        );

        SuspendEmployeeCommand cmd = mock(SuspendEmployeeCommand.class);
        when(cmd.cui()).thenReturn("cui");
        when(cmd.date()).thenReturn(LocalDate.of(2025, 2, 1));
        when(cmd.notes()).thenReturn("note");

        EmploymentHistory newPeriod = mock(EmploymentHistory.class);
        EmployeeSuspendedIntegrationEvent integrationEvent = mock(EmployeeSuspendedIntegrationEvent.class);

        try (MockedStatic<EmploymentHistory> historyStatic = mockStatic(EmploymentHistory.class);
             MockedStatic<EmployeeIntegrationMapper> mapperStatic = mockStatic(EmployeeIntegrationMapper.class)) {

            historyStatic.when(() -> EmploymentHistory.create(
                    eq(employee),
                    eq(EmploymentHistoryType.SUSPENSION),
                    eq(LocalDate.of(2025, 2, 1)),
                    eq(new BigDecimal("2000")),
                    eq("note")
            )).thenReturn(newPeriod);

            mapperStatic.when(() -> EmployeeIntegrationMapper.fromSuspended(employee))
                    .thenReturn(integrationEvent);

            Employee result = service.handle(cmd);

            assertSame(employee, result);

            verify(employee).suspend();
            verify(saveEmployee).save(employee);

            verify(active).close(LocalDate.of(2025, 2, 1));
            verify(saveHistory).save(active);
            verify(saveHistory).save(newPeriod);

            verify(publisher).publishEmployeeSuspended(integrationEvent);
        }
    }

    @Test
    void handle_throwsOverlap_whenDateNotAfterStart() {
        Employee employee = mock(Employee.class);
        EmploymentHistory active = mock(EmploymentHistory.class);
        EmploymentHistoryPeriod period = mock(EmploymentHistoryPeriod.class);

        when(active.getPeriod()).thenReturn(period);
        when(period.getStartDate()).thenReturn(LocalDate.of(2025, 2, 1));

        FindEmployeeByCui findEmployeeByCui = mock(FindEmployeeByCui.class);
        when(findEmployeeByCui.findByCui("cui")).thenReturn(Optional.of(employee));

        FindActiveEmploymentByEmployee findActive = mock(FindActiveEmploymentByEmployee.class);
        when(findActive.findActiveEmploymentPeriodByEmployee(employee)).thenReturn(Optional.of(active));

        SuspendEmployeeImpl service = new SuspendEmployeeImpl(
                findEmployeeByCui, findActive,
                mock(SaveEmployee.class),
                mock(SaveEmploymentHistory.class),
                mock(EmployeeEventPublisher.class)
        );

        SuspendEmployeeCommand cmd = mock(SuspendEmployeeCommand.class);
        when(cmd.cui()).thenReturn("cui");
        when(cmd.date()).thenReturn(LocalDate.of(2025, 2, 1));

        assertThrows(EmploymentPeriodOverlapException.class, () -> service.handle(cmd));
    }
}
