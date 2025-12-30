package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistoriesQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindAllEmploymentHistories;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllEmploymentHistoriesImplTest {

    @Mock
    private FindAllEmploymentHistories findAllEmploymentHistories;

    @InjectMocks
    private GetAllEmploymentHistoriesImpl service;

    @Test
    void shouldReturnAllHistoriesFromPort() {
        GetAllEmploymentHistoriesQuery query = mock(GetAllEmploymentHistoriesQuery.class);
        List<EmploymentHistory> expected = List.of(mock(EmploymentHistory.class), mock(EmploymentHistory.class));

        when(findAllEmploymentHistories.findAll(query)).thenReturn(expected);

        List<EmploymentHistory> result = service.handle(query);

        assertSame(expected, result);
        verify(findAllEmploymentHistories).findAll(query);
        verifyNoMoreInteractions(findAllEmploymentHistories);
    }
}
