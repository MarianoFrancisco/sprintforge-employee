package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.command.UpdateEmployeeDetailCommand;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeById;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmployee;
import com.sprintforge.employee.employee.application.port.out.storage.EmployeeImageStorage;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeProfileImageKey;
import com.sprintforge.employee.employee.application.port.in.command.ImageContent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateEmployeeDetailImplTest {

    @Test
    void handle_throws_whenEmployeeNotFound() {
        FindEmployeeById findById = mock(FindEmployeeById.class);
        when(findById.findById(any())).thenReturn(Optional.empty());

        UpdateEmployeeDetailImpl service = new UpdateEmployeeDetailImpl(
                findById,
                mock(SaveEmployee.class),
                mock(EmployeeImageStorage.class)
        );

        UpdateEmployeeDetailCommand cmd = mock(UpdateEmployeeDetailCommand.class);
        UUID id = UUID.randomUUID();
        when(cmd.id()).thenReturn(id);

        assertThrows(EmployeeNotFoundException.class, () -> service.handle(cmd));
    }

    @Test
    void handle_updatesDetails_andSaves_withoutImage() {
        Employee employee = mock(Employee.class);
        FindEmployeeById findById = mock(FindEmployeeById.class);
        when(findById.findById(any())).thenReturn(Optional.of(employee));

        SaveEmployee saveEmployee = mock(SaveEmployee.class);
        when(saveEmployee.save(employee)).thenReturn(employee);

        EmployeeImageStorage storage = mock(EmployeeImageStorage.class);

        UpdateEmployeeDetailImpl service = new UpdateEmployeeDetailImpl(findById, saveEmployee, storage);

        UpdateEmployeeDetailCommand cmd = mock(UpdateEmployeeDetailCommand.class);
        when(cmd.id()).thenReturn(UUID.randomUUID());
        when(cmd.firstName()).thenReturn("John");
        when(cmd.lastName()).thenReturn("Doe");
        when(cmd.phoneNumber()).thenReturn("555");
        when(cmd.birthDate()).thenReturn(LocalDate.of(2000, 1, 1));
        when(cmd.profileImage()).thenReturn(Optional.empty());

        Employee result = service.handle(cmd);

        assertSame(employee, result);
        verify(employee).updateDetails("John", "Doe", "555", LocalDate.of(2000, 1, 1));
        verify(storage, never()).uploadEmployeeImage(any(ImageContent.class));
        verify(saveEmployee).save(employee);
    }

    @Test
    void handle_uploadsImage_whenProvided() {
        Employee employee = mock(Employee.class);
        FindEmployeeById findById = mock(FindEmployeeById.class);
        when(findById.findById(any())).thenReturn(Optional.of(employee));

        SaveEmployee saveEmployee = mock(SaveEmployee.class);
        when(saveEmployee.save(employee)).thenReturn(employee);

        EmployeeImageStorage storage = mock(EmployeeImageStorage.class);

        EmployeeProfileImageKey key = mock(EmployeeProfileImageKey.class);
        when(key.value()).thenReturn("s3-key");

        ImageContent image = mock(ImageContent.class);

        when(storage.uploadEmployeeImage(image)).thenReturn(key);

        UpdateEmployeeDetailImpl service = new UpdateEmployeeDetailImpl(findById, saveEmployee, storage);

        UpdateEmployeeDetailCommand cmd = mock(UpdateEmployeeDetailCommand.class);
        when(cmd.id()).thenReturn(UUID.randomUUID());
        when(cmd.firstName()).thenReturn("John");
        when(cmd.lastName()).thenReturn("Doe");
        when(cmd.phoneNumber()).thenReturn("555");
        when(cmd.birthDate()).thenReturn(LocalDate.of(2000, 1, 1));
        when(cmd.profileImage()).thenReturn(Optional.of(image));

        service.handle(cmd);

        verify(storage).uploadEmployeeImage(image);
        verify(employee).setProfileImage("s3-key");
        verify(saveEmployee).save(employee);
    }
}
