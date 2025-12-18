package com.sprintforge.employee.payment.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprintforge.employee.payment.application.port.in.GetAllPayments;
import com.sprintforge.employee.payment.application.port.in.GetAllPaymentsQuery;
import com.sprintforge.employee.payment.application.port.out.persistence.FindPayments;
import com.sprintforge.employee.payment.domain.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllPaymentsImpl implements GetAllPayments {
    private final FindPayments findPayments;
    @Override
    public List<Payment> handle(GetAllPaymentsQuery query) {
        return findPayments.findAll(query);
    }
    
}
