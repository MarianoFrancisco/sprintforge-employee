package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByCui;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByCuiQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeByCui;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.out.storage.S3EmployeeImageStorage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetEmployeeByCuiImpl implements GetEmployeeByCui {

    private final FindEmployeeByCui findEmployeeByCui;
    private final S3EmployeeImageStorage employeeImageStorage;

    @Override
    public Employee handle(GetEmployeeByCuiQuery query) {
        Employee employee = findEmployeeByCui.findByCui(query.cui())
                .orElseThrow(
                        () -> EmployeeNotFoundException.byCui(query.cui()));
        if (!employee.getProfileImage().isEmpty()) {
            employee.setProfileImage(
                    employeeImageStorage.getEmployeeImageUrl(employee.getProfileImage())
                            .orElse(null));
        }

        return employee;
    }
}