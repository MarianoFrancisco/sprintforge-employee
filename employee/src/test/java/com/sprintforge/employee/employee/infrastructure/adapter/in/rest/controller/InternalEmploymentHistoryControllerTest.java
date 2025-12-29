package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.application.port.in.query.GetHiringHistoryReport;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminationHistoryReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class InternalEmploymentHistoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GetHiringHistoryReport getHiringHistoryReport;

    @Mock
    private GetTerminationHistoryReport getTerminationHistoryReport;

    @InjectMocks
    private InternalEmploymentHistoryController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetHiringReport() throws Exception {
        when(getHiringHistoryReport.handle(any()))
                .thenReturn(new EmployeesByEmploymentHistoryReportResult(
                        LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 1, 31),
                        0L,
                        List.of()
                ));

        mockMvc.perform(get("/internal-api/v1/employee/history/hiring-report")
                        .param("from", "2025-01-01")
                        .param("to", "2025-01-31"))
                .andExpect(status().isOk());

        verify(getHiringHistoryReport).handle(any());
        verifyNoInteractions(getTerminationHistoryReport);
    }

    @Test
    void shouldGetTerminationReport() throws Exception {
        when(getTerminationHistoryReport.handle(any()))
                .thenReturn(new EmployeesByEmploymentHistoryReportResult(
                        LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 1, 31),
                        0L,
                        List.of()
                ));

        mockMvc.perform(get("/internal-api/v1/employee/history/termination-report")
                        .param("from", "2025-01-01")
                        .param("to", "2025-01-31"))
                .andExpect(status().isOk());

        verify(getTerminationHistoryReport).handle(any());
        verifyNoInteractions(getHiringHistoryReport);
    }
}
