package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeById;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByIdQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeById;
import com.sprintforge.employee.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetEmployeeByIdImpl implements GetEmployeeById {

    private final FindEmployeeById findEmployeeById;

    @Override
    public Employee handle(GetEmployeeByIdQuery query) {
        return findEmployeeById.findById(query.id())
                .orElseThrow(
                        () -> new EmployeeNotFoundException("identificador", query.id().toString())
                );
    }
}