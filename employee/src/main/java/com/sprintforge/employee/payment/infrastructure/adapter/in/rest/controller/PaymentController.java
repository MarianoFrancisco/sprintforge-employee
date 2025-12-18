package com.sprintforge.employee.payment.infrastructure.adapter.in.rest.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.application.port.in.*;
import com.sprintforge.employee.payment.infrastructure.adapter.in.rest.dto.*;
import com.sprintforge.employee.payment.infrastructure.adapter.in.rest.mapper.PaymentRestMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/employee/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final GetAllPayments getAllPayments;
    private final PayEmployee payEmployee;

    @GetMapping
    public List<PaymentResponseDTO> getAll(@Valid GetAllPaymentsQuery query) {
        List<Payment> payments = getAllPayments.handle(query);
        return payments.stream()
                .map(PaymentRestMapper::toResponse)
                .toList();
    }

    @PostMapping("/{cui}")
    @ResponseStatus(CREATED)
    public PaymentResponseDTO payEmployee(
            @PathVariable String cui,
            @Validated @RequestBody PayEmployeeRequestDTO request) {
        PayEmployeeCommand command = PaymentRestMapper.toPayEmployeeCommand(cui, request);
        Payment payment = payEmployee.handle(command);
        return PaymentRestMapper.toResponse(payment);
    }

}
