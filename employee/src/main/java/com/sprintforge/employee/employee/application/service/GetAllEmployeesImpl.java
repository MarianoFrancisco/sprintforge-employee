package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmployees;
import com.sprintforge.employee.employee.application.port.in.query.GetAllEmployeesQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindAllEmployees;
import com.sprintforge.employee.employee.application.port.out.storage.EmployeeImageStorage;
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
    private final EmployeeImageStorage employeeImageStorage;

    @Override
    public List<Employee> handle(GetAllEmployeesQuery query) {

        return findAllEmployees.findAll(query)
                .stream()
                .map(employee -> {

                    if (!employee.getProfileImage().isEmpty()) {
                        employeeImageStorage
                                .getEmployeeImageUrl(employee.getProfileImage())
                                .ifPresent(employee::setProfileImage);
                    }

                    return employee;
                })
                .toList();
    }

}