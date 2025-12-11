package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByCui;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByCuiQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeByCui;
import com.sprintforge.employee.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetEmployeeByCuiImpl implements GetEmployeeByCui {

    private final FindEmployeeByCui findEmployeeByCui;

    @Override
    public Employee handle(GetEmployeeByCuiQuery query) {
        return findEmployeeByCui.findByCui(query.cui())
                .orElseThrow(
                        () -> EmployeeNotFoundException.byCui(query.cui())
                );
    }
}