package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.InvalidEmployeesException;
import com.sprintforge.employee.employee.application.port.in.command.ValidateEmployeesCommand;
import com.sprintforge.employee.employee.application.port.out.persistence.ExistEmployeesByIdIn;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidateEmployeesImplTest {

    private static final UUID ID_1 = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    @Mock
    private ExistEmployeesByIdIn existEmployeesByIdIn;

    @InjectMocks
    private ValidateEmployeesImpl serviceToTest;

    @Test
    void shouldPassWhenAllEmployeesExist() {
        when(existEmployeesByIdIn.existByIdIn(Set.of(ID_1))).thenReturn(true);

        assertDoesNotThrow(() -> serviceToTest.handle(new ValidateEmployeesCommand(Set.of(ID_1))));
        verify(existEmployeesByIdIn).existByIdIn(Set.of(ID_1));
    }

    @Test
    void shouldThrowWhenSomeEmployeeDoesNotExist() {
        when(existEmployeesByIdIn.existByIdIn(Set.of(ID_1))).thenReturn(false);

        assertThrows(InvalidEmployeesException.class,
                () -> serviceToTest.handle(new ValidateEmployeesCommand(Set.of(ID_1))));
    }
}
