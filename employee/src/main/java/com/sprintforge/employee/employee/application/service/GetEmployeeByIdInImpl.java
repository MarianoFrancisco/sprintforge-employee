package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.InvalidEmployeesException;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeesByIds;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeesByIdsQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeesByIdIn;
import com.sprintforge.employee.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetEmployeeByIdInImpl implements GetEmployeesByIds {

    private final FindEmployeesByIdIn findEmployeesByIdIn;

    @Override
    public Set<Employee> handle(GetEmployeesByIdsQuery query) {
        Set<Employee> employees = findEmployeesByIdIn.findByIdIn(query.ids());

        if (query.ids().size() != employees.size()) {
            throw new InvalidEmployeesException(query.ids());
        }
        return employees;
    }
}
