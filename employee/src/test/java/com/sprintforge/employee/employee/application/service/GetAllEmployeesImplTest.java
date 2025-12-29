package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmployeesQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindAllEmployees;
import com.sprintforge.employee.employee.application.port.out.storage.EmployeeImageStorage;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeProfileImageKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllEmployeesImplTest {

    @Mock
    private FindAllEmployees findAllEmployees;
    @Mock
    private EmployeeImageStorage employeeImageStorage;

    @InjectMocks
    private GetAllEmployeesImpl serviceToTest;

    @Test
    void shouldReturnEmployeesWithoutResolvingImageWhenEmpty() {
        GetAllEmployeesQuery query = mock(GetAllEmployeesQuery.class);

        Employee employee = mock(Employee.class);
        EmployeeProfileImageKey emptyKey = EmployeeProfileImageKey.empty();

        when(employee.getProfileImage()).thenReturn(emptyKey);
        when(findAllEmployees.findAll(query)).thenReturn(List.of(employee));

        List<Employee> result = serviceToTest.handle(query);

        assertEquals(1, result.size());
        verify(findAllEmployees).findAll(query);
        verifyNoInteractions(employeeImageStorage);
    }

    @Test
    void shouldResolveImageUrlWhenProfileImageIsNotEmpty() {
        GetAllEmployeesQuery query = mock(GetAllEmployeesQuery.class);

        Employee employee = mock(Employee.class);
        EmployeeProfileImageKey key = new EmployeeProfileImageKey("img-key");

        when(employee.getProfileImage()).thenReturn(key);
        when(findAllEmployees.findAll(query)).thenReturn(List.of(employee));

        String url = "https://cdn/img-key";
        when(employeeImageStorage.getEmployeeImageUrl(key)).thenReturn(Optional.of(url));

        List<Employee> result = serviceToTest.handle(query);

        assertEquals(1, result.size());
        verify(employeeImageStorage).getEmployeeImageUrl(key);
        verify(employee).setProfileImage(url);
    }
}
