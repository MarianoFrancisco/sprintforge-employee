package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.sprintforge.employee.employee.application.port.in.query.GetEmployeesByIds;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeesByIdsQuery;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.test.fixtures.EmployeeFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class InternalEmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GetEmployeesByIds getEmployeesByIds;

    @InjectMocks
    private InternalEmployeeController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetEmployeesByIds() throws Exception {
        Employee employee1 = EmployeeFixture.validEmployee();
        Employee employee2 = EmployeeFixture.validEmployee2();

        when(getEmployeesByIds.handle(any(GetEmployeesByIdsQuery.class)))
                .thenReturn(Set.of(employee1, employee2));

        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        mockMvc.perform(post("/internal-api/v1/employee/get-by-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"" + id1 + "\",\"" + id2 + "\"]"))
                .andExpect(status().isOk());

        verify(getEmployeesByIds).handle(any(GetEmployeesByIdsQuery.class));
        verifyNoMoreInteractions(getEmployeesByIds);
    }
}
