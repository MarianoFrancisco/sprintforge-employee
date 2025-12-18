package com.sprintforge.employee.payment.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeByCui;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.payment.application.exception.DuplicatePaymentException;
import com.sprintforge.employee.payment.application.exception.PaymentDateBeforeLastException;
import com.sprintforge.employee.payment.application.mapper.PaymentMapper;
import com.sprintforge.employee.payment.application.port.in.PayEmployee;
import com.sprintforge.employee.payment.application.port.in.PayEmployeeCommand;
import com.sprintforge.employee.payment.application.port.out.persistence.*;
import com.sprintforge.employee.payment.domain.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PayEmployeeImpl implements PayEmployee {
    private final FindEmployeeByCui findEmployeeByCui;
    private final FindPayments findPayments;
    private final SavePayment savePayment;
    @Override
    public Payment handle(PayEmployeeCommand command) {
        Employee employee = findEmployeeByCui.findByCui(command.cui())
                .orElseThrow(() -> EmployeeNotFoundException.byCui(command.cui()));
        
        if (findPayments.existsByEmployeeAndDate(employee, command.date())) {
            throw new DuplicatePaymentException(command.cui(), command.date());
        }

        findPayments.findLastPaymentByEmployee(employee).ifPresent(
            lastPayment -> {
                if (!command.date().isAfter(lastPayment.getDate().value())) {
                    throw new PaymentDateBeforeLastException(command.cui(), lastPayment.getDate().value());
                }
            }
        );

        Payment payment = PaymentMapper.toDomain(command, employee);
        savePayment.save(payment);

        return payment;
    }
    
}
