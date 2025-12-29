package com.sprintforge.employee.payment.infrastructure.adapter.in.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprintforge.employee.payment.application.port.in.GetAllPayments;
import com.sprintforge.employee.payment.application.port.in.GetAllPaymentsQuery;
import com.sprintforge.employee.payment.application.port.in.PayEmployee;
import com.sprintforge.employee.payment.application.port.in.PayEmployeeCommand;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.infrastructure.adapter.in.rest.dto.PayEmployeeRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    private static final String BASE_URL = "/api/v1/employee/payment";
    private static final String CUI = "1234567890101";

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private GetAllPayments getAllPayments;
    @Mock
    private PayEmployee payEmployee;

    @InjectMocks
    private PaymentController controller;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetAllPayments() throws Exception {
        when(getAllPayments.handle(any(GetAllPaymentsQuery.class))).thenReturn(List.of(mock(Payment.class)));

        mockMvc.perform(get(BASE_URL)
                        .param("employee", "john")
                        .param("position", "dev"))
                .andExpect(status().isOk());

        verify(getAllPayments).handle(any(GetAllPaymentsQuery.class));
    }

    @Test
    void shouldPayEmployee() throws Exception {
        PayEmployeeRequestDTO dto = new PayEmployeeRequestDTO(
                LocalDate.of(2025, 1, 15),
                new BigDecimal("100.00"),
                new BigDecimal("50.00"),
                "notes"
        );

        when(payEmployee.handle(any(PayEmployeeCommand.class))).thenReturn(mock(Payment.class));

        mockMvc.perform(post(BASE_URL + "/{cui}", CUI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(payEmployee).handle(any(PayEmployeeCommand.class));
    }
}
