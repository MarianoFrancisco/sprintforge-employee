package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByCuiQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeByCui;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeProfileImageKey;
import com.sprintforge.employee.employee.infrastructure.adapter.out.storage.S3EmployeeImageStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetEmployeeByCuiImplTest {

    private static final String CUI = "1234567890101";

    @Mock
    private FindEmployeeByCui findEmployeeByCui;
    @Mock
    private S3EmployeeImageStorage employeeImageStorage;

    @InjectMocks
    private GetEmployeeByCuiImpl serviceToTest;

    @Test
    void shouldReturnEmployeeWhenFound() {
        Employee employee = mock(Employee.class);
        when(findEmployeeByCui.findByCui(CUI)).thenReturn(Optional.of(employee));
        when(employee.getProfileImage()).thenReturn(EmployeeProfileImageKey.empty());

        Employee result = serviceToTest.handle(new GetEmployeeByCuiQuery(CUI));

        assertNotNull(result);
        verify(findEmployeeByCui).findByCui(CUI);
        verifyNoInteractions(employeeImageStorage);
    }

    @Test
    void shouldResolveImageUrlWhenNotEmpty() {
        Employee employee = mock(Employee.class);
        EmployeeProfileImageKey key = new EmployeeProfileImageKey("img-key");

        when(findEmployeeByCui.findByCui(CUI)).thenReturn(Optional.of(employee));
        when(employee.getProfileImage()).thenReturn(key);

        String url = "https://cdn/img-key";
        when(employeeImageStorage.getEmployeeImageUrl(key)).thenReturn(Optional.of(url));

        Employee result = serviceToTest.handle(new GetEmployeeByCuiQuery(CUI));

        assertNotNull(result);
        verify(employeeImageStorage).getEmployeeImageUrl(key);
        verify(employee).setProfileImage(url);
    }

    @Test
    void shouldThrowWhenNotFound() {
        when(findEmployeeByCui.findByCui(CUI)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,
                () -> serviceToTest.handle(new GetEmployeeByCuiQuery(CUI)));

        verify(findEmployeeByCui).findByCui(CUI);
        verifyNoInteractions(employeeImageStorage);
    }
}
