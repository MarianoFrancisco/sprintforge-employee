package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.DuplicateEmployeeException;
import com.sprintforge.employee.employee.application.mapper.EmployeeIntegrationMapper;
import com.sprintforge.employee.employee.application.mapper.EmployeeMapper;
import com.sprintforge.employee.employee.application.port.in.command.HireEmployeeCommand;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeEventPublisher;
import com.sprintforge.employee.employee.application.port.out.persistence.ExistsEmployeeByCui;
import com.sprintforge.employee.employee.application.port.out.persistence.ExistsEmployeeByEmail;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmployee;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmploymentHistory;
import com.sprintforge.employee.employee.application.port.out.storage.EmployeeImageStorage;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.position.application.exception.PositionNotFoundException;
import com.sprintforge.employee.position.application.port.out.persistence.FindPositionById;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HireEmployeeImplTest {

    private static final String CUI = "1234567890101";
    private static final String EMAIL = "john.doe@test.com";
    private static final UUID POSITION_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

    @Mock
    private ExistsEmployeeByCui existsEmployeeByCui;
    @Mock
    private ExistsEmployeeByEmail existsEmployeeByEmail;
    @Mock
    private FindPositionById findPositionById;
    @Mock
    private SaveEmployee saveEmployee;
    @Mock
    private SaveEmploymentHistory saveEmploymentHistory;
    @Mock
    private EmployeeEventPublisher employeeEventPublisher;
    @Mock
    private EmployeeImageStorage employeeImageStorage;

    @InjectMocks
    private HireEmployeeImpl serviceToTest;

    @Test
    void shouldThrowWhenDuplicateCui() {
        HireEmployeeCommand command = mock(HireEmployeeCommand.class);
        when(command.cui()).thenReturn(CUI);

        when(existsEmployeeByCui.existsByCui(CUI)).thenReturn(true);

        assertThrows(DuplicateEmployeeException.class, () -> serviceToTest.handle(command));

        verify(existsEmployeeByCui).existsByCui(CUI);
        verifyNoMoreInteractions(existsEmployeeByEmail, findPositionById, saveEmployee, saveEmploymentHistory, employeeEventPublisher, employeeImageStorage);
    }

    @Test
    void shouldThrowWhenDuplicateEmail() {
        HireEmployeeCommand command = mock(HireEmployeeCommand.class);
        when(command.cui()).thenReturn(CUI);
        when(command.email()).thenReturn(EMAIL);

        when(existsEmployeeByCui.existsByCui(CUI)).thenReturn(false);
        when(existsEmployeeByEmail.existsByEmail(EMAIL)).thenReturn(true);

        assertThrows(DuplicateEmployeeException.class, () -> serviceToTest.handle(command));

        verify(existsEmployeeByEmail).existsByEmail(EMAIL);
        verifyNoMoreInteractions(findPositionById, saveEmployee, saveEmploymentHistory, employeeEventPublisher, employeeImageStorage);
    }

    @Test
    void shouldThrowWhenPositionNotFound() {
        HireEmployeeCommand command = mock(HireEmployeeCommand.class);
        when(command.cui()).thenReturn(CUI);
        when(command.email()).thenReturn(EMAIL);
        when(command.positionId()).thenReturn(POSITION_ID);

        when(existsEmployeeByCui.existsByCui(CUI)).thenReturn(false);
        when(existsEmployeeByEmail.existsByEmail(EMAIL)).thenReturn(false);
        when(findPositionById.findById(POSITION_ID)).thenReturn(Optional.empty());

        assertThrows(PositionNotFoundException.class, () -> serviceToTest.handle(command));

        verify(findPositionById).findById(POSITION_ID);
        verifyNoInteractions(saveEmployee, saveEmploymentHistory, employeeEventPublisher);
    }

    @Test
    void shouldHireEmployeeSuccessfullyAndPublishEvent() {
        HireEmployeeCommand command = mock(HireEmployeeCommand.class);
        when(command.cui()).thenReturn(CUI);
        when(command.email()).thenReturn(EMAIL);
        when(command.positionId()).thenReturn(POSITION_ID);
        when(command.startDate()).thenReturn(LocalDate.of(2025, 1, 1));
        when(command.salary()).thenReturn(new BigDecimal("5000.00"));
        when(command.notes()).thenReturn("hire");
        when(command.profileImage()).thenReturn(Optional.empty());

        Position position = mock(Position.class);
        Employee employee = mock(Employee.class);
        Employee saved = mock(Employee.class);
        EmployeeCreatedIntegrationEvent event = mock(EmployeeCreatedIntegrationEvent.class);

        when(existsEmployeeByCui.existsByCui(CUI)).thenReturn(false);
        when(existsEmployeeByEmail.existsByEmail(EMAIL)).thenReturn(false);
        when(findPositionById.findById(POSITION_ID)).thenReturn(Optional.of(position));
        when(saveEmployee.save(employee)).thenReturn(saved);

        try (MockedStatic<EmployeeMapper> mapper = mockStatic(EmployeeMapper.class);
             MockedStatic<EmployeeIntegrationMapper> integration = mockStatic(EmployeeIntegrationMapper.class)) {

            mapper.when(() -> EmployeeMapper.toDomain(command, position)).thenReturn(employee);
            integration.when(() -> EmployeeIntegrationMapper.from(saved)).thenReturn(event);

            Employee result = serviceToTest.handle(command);

            assertNotNull(result);
            verify(saveEmployee).save(employee);
            verify(saveEmploymentHistory).save(any());
            verify(employeeEventPublisher).publishEmployeeCreated(event);
        }
    }
}
