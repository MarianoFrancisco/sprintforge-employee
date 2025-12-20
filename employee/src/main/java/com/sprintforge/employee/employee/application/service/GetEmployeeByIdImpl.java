package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeById;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByIdQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeById;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.out.storage.S3EmployeeImageStorage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetEmployeeByIdImpl implements GetEmployeeById {

    private final FindEmployeeById findEmployeeById;
    private final S3EmployeeImageStorage employeeImageStorage;

    @Override
    public Employee handle(GetEmployeeByIdQuery query) {
        Employee employee = findEmployeeById.findById(query.id())
                .orElseThrow(
                        () -> EmployeeNotFoundException.byId(query.id()));

        if (!employee.getProfileImage().isEmpty()) {
            employee.setProfileImage(
                    employeeImageStorage.getEmployeeImageUrl(employee.getProfileImage())
                            .orElse(null));
        }

        return employee;
    }
}