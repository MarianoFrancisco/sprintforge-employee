package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.exception.EmploymentHistoryNotFound;
import com.sprintforge.employee.employee.application.exception.EmploymentPeriodOverlapException;
import com.sprintforge.employee.employee.application.port.in.command.IncreaseEmployeeSalaryCommand;
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

class IncreaseEmployeeSalaryImplTest {

    @Test
    void handle_throwsEmployeeNotFound_whenNoEmployee() {
        FindEmployeeByCui findEmployeeByCui = mock(FindEmployeeByCui.class);
        when(findEmployeeByCui.findByCui("cui")).thenReturn(Optional.empty());

        IncreaseEmployeeSalaryImpl service = new IncreaseEmployeeSalaryImpl(
                findEmployeeByCui,
                mock(FindActiveEmploymentByEmployee.class),
                mock(SaveEmployee.class),
                mock(SaveEmploymentHistory.class)
        );

        IncreaseEmployeeSalaryCommand cmd = mock(IncreaseEmployeeSalaryCommand.class);
        when(cmd.cui()).thenReturn("cui");

        assertThrows(EmployeeNotFoundException.class, () -> service.handle(cmd));
    }

    @Test
    void handle_throwsHistoryNotFound_whenNoActivePeriod() {
        Employee employee = mock(Employee.class);

        FindEmployeeByCui findEmployeeByCui = mock(FindEmployeeByCui.class);
        when(findEmployeeByCui.findByCui("cui")).thenReturn(Optional.of(employee));

        FindActiveEmploymentByEmployee findActive = mock(FindActiveEmploymentByEmployee.class);
        when(findActive.findActiveEmploymentPeriodByEmployee(employee)).thenReturn(Optional.empty());

        IncreaseEmployeeSalaryImpl service = new IncreaseEmployeeSalaryImpl(
                findEmployeeByCui, findActive,
                mock(SaveEmployee.class),
                mock(SaveEmploymentHistory.class)
        );

        IncreaseEmployeeSalaryCommand cmd = mock(IncreaseEmployeeSalaryCommand.class);
        when(cmd.cui()).thenReturn("cui");

        assertThrows(EmploymentHistoryNotFound.class, () -> service.handle(cmd));
    }

    @Test
    void handle_throwsOverlap_whenDateNotAfterStart() {
        Employee employee = mock(Employee.class);
        EmploymentHistory active = mock(EmploymentHistory.class);
        EmploymentHistoryPeriod period = mock(EmploymentHistoryPeriod.class);

        when(active.getPeriod()).thenReturn(period);
        when(period.getStartDate()).thenReturn(LocalDate.of(2025, 1, 10));

        FindEmployeeByCui findEmployeeByCui = mock(FindEmployeeByCui.class);
        when(findEmployeeByCui.findByCui("cui")).thenReturn(Optional.of(employee));

        FindActiveEmploymentByEmployee findActive = mock(FindActiveEmploymentByEmployee.class);
        when(findActive.findActiveEmploymentPeriodByEmployee(employee)).thenReturn(Optional.of(active));

        IncreaseEmployeeSalaryImpl service = new IncreaseEmployeeSalaryImpl(
                findEmployeeByCui, findActive,
                mock(SaveEmployee.class),
                mock(SaveEmploymentHistory.class)
        );

        IncreaseEmployeeSalaryCommand cmd = mock(IncreaseEmployeeSalaryCommand.class);
        when(cmd.cui()).thenReturn("cui");
        when(cmd.date()).thenReturn(LocalDate.of(2025, 1, 10));
        assertThrows(EmploymentPeriodOverlapException.class, () -> service.handle(cmd));
    }

    @Test
    void handle_happyPath_increasesSalary_closesPeriod_savesHistoryTwice() {
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

        IncreaseEmployeeSalaryImpl service = new IncreaseEmployeeSalaryImpl(
                findEmployeeByCui, findActive, saveEmployee, saveHistory
        );

        IncreaseEmployeeSalaryCommand cmd = mock(IncreaseEmployeeSalaryCommand.class);
        when(cmd.cui()).thenReturn("cui");
        when(cmd.date()).thenReturn(LocalDate.of(2025, 2, 1));
        when(cmd.increaseAmount()).thenReturn(new BigDecimal("100"));
        when(cmd.notes()).thenReturn("note");

        EmploymentHistory newPeriod = mock(EmploymentHistory.class);

        try (MockedStatic<EmploymentHistory> mocked = mockStatic(EmploymentHistory.class)) {
            mocked.when(() -> EmploymentHistory.create(
                    eq(employee),
                    eq(EmploymentHistoryType.SALARY_INCREASE),
                    eq(LocalDate.of(2025, 2, 1)),
                    eq(new BigDecimal("2000")),
                    eq("note")
            )).thenReturn(newPeriod);

            Employee result = service.handle(cmd);

            assertSame(employee, result);
            verify(employee).increaseSalary(new BigDecimal("100"));
            verify(saveEmployee).save(employee);

            verify(active).close(LocalDate.of(2025, 2, 1));
            verify(saveHistory).save(active);
            verify(saveHistory).save(newPeriod);
        }
    }
}
