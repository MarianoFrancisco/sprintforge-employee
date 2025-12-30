package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByEmailQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeByEmail;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeProfileImageKey;
import com.sprintforge.employee.employee.infrastructure.adapter.out.storage.S3EmployeeImageStorage;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetEmployeeByEmailImplTest {

    @Test
    void handle_throws_whenEmployeeNotFound() {
        FindEmployeeByEmail findEmployeeByEmail = mock(FindEmployeeByEmail.class);
        S3EmployeeImageStorage storage = mock(S3EmployeeImageStorage.class);

        GetEmployeeByEmailImpl service = new GetEmployeeByEmailImpl(findEmployeeByEmail, storage);

        GetEmployeeByEmailQuery query = mock(GetEmployeeByEmailQuery.class);
        when(query.email()).thenReturn("john@test.com");
        when(findEmployeeByEmail.findByEmail("john@test.com")).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> service.handle(query));

        verify(findEmployeeByEmail).findByEmail("john@test.com");
        verifyNoInteractions(storage);
    }

    @Test
    void handle_doesNotCallStorage_whenProfileImageIsEmpty() {
        FindEmployeeByEmail findEmployeeByEmail = mock(FindEmployeeByEmail.class);
        S3EmployeeImageStorage storage = mock(S3EmployeeImageStorage.class);

        GetEmployeeByEmailImpl service = new GetEmployeeByEmailImpl(findEmployeeByEmail, storage);

        GetEmployeeByEmailQuery query = mock(GetEmployeeByEmailQuery.class);
        when(query.email()).thenReturn("john@test.com");

        Employee employee = mock(Employee.class);
        EmployeeProfileImageKey emptyKey = EmployeeProfileImageKey.empty();

        when(findEmployeeByEmail.findByEmail("john@test.com")).thenReturn(Optional.of(employee));
        when(employee.getProfileImage()).thenReturn(emptyKey);

        Employee result = service.handle(query);

        assertSame(employee, result);
        verify(employee, never()).setProfileImage(any());
        verifyNoInteractions(storage);
    }

    @Test
    void handle_setsProfileImageUrl_whenProfileImageNotEmpty_andStorageReturnsUrl() {
        FindEmployeeByEmail findEmployeeByEmail = mock(FindEmployeeByEmail.class);
        S3EmployeeImageStorage storage = mock(S3EmployeeImageStorage.class);

        GetEmployeeByEmailImpl service = new GetEmployeeByEmailImpl(findEmployeeByEmail, storage);

        GetEmployeeByEmailQuery query = mock(GetEmployeeByEmailQuery.class);
        when(query.email()).thenReturn("john@test.com");

        Employee employee = mock(Employee.class);

        EmployeeProfileImageKey key = new EmployeeProfileImageKey("img-key");
        when(findEmployeeByEmail.findByEmail("john@test.com")).thenReturn(Optional.of(employee));
        when(employee.getProfileImage()).thenReturn(key);

        when(storage.getEmployeeImageUrl(key)).thenReturn(Optional.of("https://cdn/img.png"));

        Employee result = service.handle(query);

        assertSame(employee, result);
        verify(storage).getEmployeeImageUrl(key);
        verify(employee).setProfileImage("https://cdn/img.png");
    }

    @Test
    void handle_setsProfileImageNull_whenProfileImageNotEmpty_andStorageReturnsEmpty() {
        FindEmployeeByEmail findEmployeeByEmail = mock(FindEmployeeByEmail.class);
        S3EmployeeImageStorage storage = mock(S3EmployeeImageStorage.class);

        GetEmployeeByEmailImpl service = new GetEmployeeByEmailImpl(findEmployeeByEmail, storage);

        GetEmployeeByEmailQuery query = mock(GetEmployeeByEmailQuery.class);
        when(query.email()).thenReturn("john@test.com");

        Employee employee = mock(Employee.class);

        EmployeeProfileImageKey key = new EmployeeProfileImageKey("img-key");
        when(findEmployeeByEmail.findByEmail("john@test.com")).thenReturn(Optional.of(employee));
        when(employee.getProfileImage()).thenReturn(key);

        when(storage.getEmployeeImageUrl(key)).thenReturn(Optional.empty());

        Employee result = service.handle(query);

        assertSame(employee, result);
        verify(storage).getEmployeeImageUrl(key);
        verify(employee).setProfileImage(null);
    }
}
