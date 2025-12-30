package com.sprintforge.employee.payment.infrastructure.adapter.in.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeCui;
import com.sprintforge.employee.payment.application.port.in.GetAllPayments;
import com.sprintforge.employee.payment.application.port.in.GetAllPaymentsQuery;
import com.sprintforge.employee.payment.application.port.in.PayEmployee;
import com.sprintforge.employee.payment.application.port.in.PayEmployeeCommand;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.domain.valueobject.PaymentDate;
import com.sprintforge.employee.payment.domain.valueobject.PaymentNotes;
import com.sprintforge.employee.payment.infrastructure.adapter.in.rest.dto.PayEmployeeRequestDTO;
import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.domain.valueobject.PositionId;
import com.sprintforge.employee.position.domain.valueobject.PositionName;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetAllPayments() throws Exception {
        Employee employee = mock(Employee.class);
        Position position = mock(Position.class);

        when(employee.getCui()).thenReturn(new EmployeeCui(CUI));
        when(employee.getPosition()).thenReturn(position);
        when(position.getId()).thenReturn(new PositionId(UUID.randomUUID()));
        when(position.getName()).thenReturn(new PositionName("Developer"));

        Payment payment = Payment.rehydrate(
                UUID.randomUUID(),
                employee,
                new PaymentDate(LocalDate.of(2025, 1, 15)),
                new Money(new BigDecimal("5000.00")),
                new Money(new BigDecimal("100.00")),
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("5050.00")),
                new PaymentNotes("notes")
        );

        when(getAllPayments.handle(any(GetAllPaymentsQuery.class))).thenReturn(List.of(payment));

        mockMvc.perform(get(BASE_URL)
                        .param("employee", "john")
                        .param("position", "dev"))
                .andExpect(status().isOk());

        verify(getAllPayments).handle(any(GetAllPaymentsQuery.class));
        verifyNoMoreInteractions(getAllPayments);
    }

    @Test
    void shouldPayEmployee() throws Exception {
        PayEmployeeRequestDTO dto = new PayEmployeeRequestDTO(
                LocalDate.of(2025, 1, 15),
                new BigDecimal("100.00"),
                new BigDecimal("50.00"),
                "notes"
        );

        Employee employee = mock(Employee.class);
        Position position = mock(Position.class);

        when(employee.getCui()).thenReturn(new EmployeeCui(CUI));
        when(employee.getPosition()).thenReturn(position);
        when(position.getId()).thenReturn(new PositionId(UUID.randomUUID()));
        when(position.getName()).thenReturn(new PositionName("Developer"));

        Payment payment = Payment.rehydrate(
                UUID.randomUUID(),
                employee,
                new PaymentDate(LocalDate.of(2025, 1, 15)),
                new Money(new BigDecimal("5000.00")),
                new Money(new BigDecimal("100.00")),
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("5050.00")),
                new PaymentNotes("notes")
        );

        when(payEmployee.handle(any(PayEmployeeCommand.class))).thenReturn(payment);

        mockMvc.perform(post(BASE_URL + "/{cui}", CUI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(payEmployee).handle(any(PayEmployeeCommand.class));
        verifyNoMoreInteractions(payEmployee);
    }
}
