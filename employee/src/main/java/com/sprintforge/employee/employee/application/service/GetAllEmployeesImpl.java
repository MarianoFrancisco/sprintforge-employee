package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.port.in.employee.getall.GetAllEmployees;
import com.sprintforge.employee.employee.application.port.in.employee.getall.GetAllEmployeesQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindAllEmployees;
import com.sprintforge.employee.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllEmployeesImpl implements GetAllEmployees {

    private final FindAllEmployees findAllEmployees;

    @Override
    public List<Employee> handle(GetAllEmployeesQuery query) {
        return findAllEmployees.findAll(query);
    }
}