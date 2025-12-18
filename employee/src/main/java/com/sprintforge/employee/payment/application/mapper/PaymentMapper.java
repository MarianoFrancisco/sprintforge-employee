package com.sprintforge.employee.payment.application.mapper;

import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.payment.application.port.in.PayEmployeeCommand;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.domain.valueobject.PaymentDate;
import com.sprintforge.employee.payment.domain.valueobject.PaymentNotes;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentMapper {
    public Payment toDomain(PayEmployeeCommand command, Employee employee) {
        return Payment.create(
                employee,
                new PaymentDate(command.date()),
                new Money(command.bonus()),
                new Money(command.deduction()),
                new PaymentNotes(command.notes())
        );
    }
}
