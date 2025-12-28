package com.sprintforge.employee.payment.application.port.out.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.payment.application.port.in.GetAllPaymentsQuery;
import com.sprintforge.employee.payment.domain.Payment;

public interface FindPayments {
    List<Payment> findAll(GetAllPaymentsQuery query);
    Optional<Payment> findLastPaymentByEmployee(Employee employee);
    boolean existsByEmployeeAndDate(Employee employee, LocalDate date);
}
