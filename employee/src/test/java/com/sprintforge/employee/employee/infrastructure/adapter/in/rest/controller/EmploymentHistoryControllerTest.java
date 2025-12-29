package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistories;
import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistoriesQuery;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

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
        when(getAllEmploymentHistories.handle(any(GetAllEmploymentHistoriesQuery.class)))
                .thenReturn(List.of(mock(EmploymentHistory.class)));

        mockMvc.perform(get("/api/v1/employee/history"))
                .andExpect(status().isOk());

        verify(getAllEmploymentHistories).handle(any(GetAllEmploymentHistoriesQuery.class));
    }
}
