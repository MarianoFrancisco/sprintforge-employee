package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprintforge.employee.employee.application.port.in.command.ValidateEmployees;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeesByIds;
import com.sprintforge.employee.employee.domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class InternalEmployeeControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private GetEmployeesByIds getEmployeesByIdIn;

    @Mock
    private ValidateEmployees validateEmployees;

    @InjectMocks
    private InternalEmployeeController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetEmployeesByIds() throws Exception {
        when(getEmployeesByIdIn.handle(any())).thenReturn(Set.of(mock(Employee.class)));

        mockMvc.perform(post("/internal-api/v1/employee/get-by-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "ids": ["aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"] }
                                """))
                .andExpect(status().isOk());

        verify(getEmployeesByIdIn).handle(any());
        verifyNoInteractions(validateEmployees);
    }

    @Test
    void shouldValidateEmployeesIds() throws Exception {
        doNothing().when(validateEmployees).handle(any());

        mockMvc.perform(post("/internal-api/v1/employee/validate-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "ids": ["aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"] }
                                """))
                .andExpect(status().isNoContent());

        verify(validateEmployees).handle(any());
        verifyNoInteractions(getEmployeesByIdIn);
    }
}
