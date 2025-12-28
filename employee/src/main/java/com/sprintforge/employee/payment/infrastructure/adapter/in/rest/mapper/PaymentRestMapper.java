package com.sprintforge.employee.payment.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.payment.application.port.in.PayEmployeeCommand;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.infrastructure.adapter.in.rest.dto.PayEmployeeRequestDTO;
import com.sprintforge.employee.payment.infrastructure.adapter.in.rest.dto.PaymentResponseDTO;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentRestMapper {
    public PaymentResponseDTO toResponse(Payment payment) {
        Employee employee = payment.getEmployee();
        return new PaymentResponseDTO(
            employee.getCui().value(),
            employee.getFullName(),
            employee.getPosition().getId().value(),
            employee.getPosition().getName().value(),
            payment.getDate().value(),
            payment.getBaseSalary().amount(),
            payment.getBonus().amount(),
            payment.getDeduction().amount(),
            payment.getTotal().amount(),
            payment.getNotes().value()
        );
    }

    public PayEmployeeCommand toPayEmployeeCommand(String cui, PayEmployeeRequestDTO request) {
        return new PayEmployeeCommand(
            cui,
            request.date(),
            request.bonus(),
            request.deduction(),
            request.notes()
        );
    }
}
