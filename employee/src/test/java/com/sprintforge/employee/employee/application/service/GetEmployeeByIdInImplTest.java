package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.InvalidEmployeesException;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeesByIdsQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeesByIdIn;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeProfileImageKey;
import com.sprintforge.employee.employee.infrastructure.adapter.out.storage.S3EmployeeImageStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetEmployeeByIdInImplTest {

    private static final UUID ID_1 = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID ID_2 = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

    @Mock
    private FindEmployeesByIdIn findEmployeesByIdIn;
    @Mock
    private S3EmployeeImageStorage employeeImageStorage;

    @InjectMocks
    private GetEmployeeByIdInImpl serviceToTest;

    @Test
    void shouldReturnEmployeesAndResolveImages() {
        Employee e1 = mock(Employee.class);
        Employee e2 = mock(Employee.class);

        EmployeeProfileImageKey k1 = new EmployeeProfileImageKey("k1");

        when(findEmployeesByIdIn.findByIdIn(Set.of(ID_1, ID_2))).thenReturn(Set.of(e1, e2));

        when(e1.getProfileImage()).thenReturn(k1);
        when(employeeImageStorage.getEmployeeImageUrl(k1)).thenReturn(Optional.of("url1"));

        when(e2.getProfileImage()).thenReturn(EmployeeProfileImageKey.empty());

        Set<Employee> result = serviceToTest.handle(new GetEmployeesByIdsQuery(Set.of(ID_1, ID_2)));

        assertEquals(2, result.size());
        verify(employeeImageStorage).getEmployeeImageUrl(k1);
        verify(e1).setProfileImage("url1");
        verifyNoMoreInteractions(employeeImageStorage);
    }

    @Test
    void shouldThrowWhenSomeIdsAreInvalid() {
        Employee e1 = mock(Employee.class);
        when(findEmployeesByIdIn.findByIdIn(Set.of(ID_1, ID_2))).thenReturn(Set.of(e1));

        assertThrows(InvalidEmployeesException.class,
                () -> serviceToTest.handle(new GetEmployeesByIdsQuery(Set.of(ID_1, ID_2))));

        verifyNoInteractions(employeeImageStorage);
    }
}
