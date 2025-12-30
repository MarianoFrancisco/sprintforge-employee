package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistories;
import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistoriesQuery;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.test.fixtures.EmployeeFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EmploymentHistoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GetAllEmploymentHistories getAllEmploymentHistories;

    @InjectMocks
    private EmploymentHistoryController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetAllEmploymentHistories() throws Exception {
        Employee employee = EmployeeFixture.validEmployee();

        EmploymentHistory history = EmploymentHistory.rehydrate(
                UUID.randomUUID(),
                employee,
                EmploymentHistoryType.HIRING,
                LocalDate.of(2025, 1, 1),
                null,
                BigDecimal.valueOf(5000),
                "notes"
        );

        when(getAllEmploymentHistories.handle(any(GetAllEmploymentHistoriesQuery.class)))
                .thenReturn(List.of(history));

        mockMvc.perform(get("/api/v1/employee/history"))
                .andExpect(status().isOk());

        verify(getAllEmploymentHistories).handle(any(GetAllEmploymentHistoriesQuery.class));
        verifyNoMoreInteractions(getAllEmploymentHistories);
    }
}
