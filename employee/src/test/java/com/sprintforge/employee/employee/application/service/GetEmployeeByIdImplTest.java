package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByIdQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeById;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeProfileImageKey;
import com.sprintforge.employee.employee.infrastructure.adapter.out.storage.S3EmployeeImageStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetEmployeeByIdImplTest {

    @Mock private FindEmployeeById findEmployeeById;
    @Mock private S3EmployeeImageStorage employeeImageStorage;

    @InjectMocks private GetEmployeeByIdImpl service;

    @Test
    void shouldThrowWhenNotFound() {
        UUID id = UUID.randomUUID();
        GetEmployeeByIdQuery query = mock(GetEmployeeByIdQuery.class);
        when(query.id()).thenReturn(id);

        when(findEmployeeById.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> service.handle(query));
        verify(findEmployeeById).findById(id);
        verifyNoInteractions(employeeImageStorage);
    }

    @Test
    void shouldReturnEmployeeWithoutCallingS3WhenProfileImageEmpty() {
        UUID id = UUID.randomUUID();
        GetEmployeeByIdQuery query = mock(GetEmployeeByIdQuery.class);
        when(query.id()).thenReturn(id);

        Employee employee = mock(Employee.class);
        EmployeeProfileImageKey key = mock(EmployeeProfileImageKey.class);

        when(findEmployeeById.findById(id)).thenReturn(Optional.of(employee));
        when(employee.getProfileImage()).thenReturn(key);
        when(key.isEmpty()).thenReturn(true);

        Employee result = service.handle(query);

        assertSame(employee, result);
        verify(findEmployeeById).findById(id);
        verify(employee).getProfileImage();
        verify(key).isEmpty();
        verifyNoInteractions(employeeImageStorage);
    }

    @Test
    void shouldFetchImageUrlWhenProfileImageNotEmpty() {
        UUID id = UUID.randomUUID();
        GetEmployeeByIdQuery query = mock(GetEmployeeByIdQuery.class);
        when(query.id()).thenReturn(id);

        Employee employee = mock(Employee.class);
        EmployeeProfileImageKey key = mock(EmployeeProfileImageKey.class);

        when(findEmployeeById.findById(id)).thenReturn(Optional.of(employee));
        when(employee.getProfileImage()).thenReturn(key);
        when(key.isEmpty()).thenReturn(false);

        when(employeeImageStorage.getEmployeeImageUrl(key)).thenReturn(Optional.of("https://s3/url.png"));

        Employee result = service.handle(query);

        assertSame(employee, result);
        verify(employeeImageStorage).getEmployeeImageUrl(key);
        verify(employee).setProfileImage("https://s3/url.png");
    }
}
