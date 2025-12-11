package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByEmail;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByEmailQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeByEmail;
import com.sprintforge.employee.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetEmployeeByEmailImpl implements GetEmployeeByEmail {

    private final FindEmployeeByEmail findEmployeeByEmail;

    @Override
    public Employee handle(GetEmployeeByEmailQuery query) {
        return findEmployeeByEmail.findByEmail(query.email())
                .orElseThrow(
                        () -> EmployeeNotFoundException.byEmail(query.email())
                );
    }
}