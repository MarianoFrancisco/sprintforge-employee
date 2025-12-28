package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.InvalidEmployeesException;
import com.sprintforge.employee.employee.application.port.in.command.ValidateEmployees;
import com.sprintforge.employee.employee.application.port.in.command.ValidateEmployeesCommand;
import com.sprintforge.employee.employee.application.port.out.persistence.ExistEmployeesByIdIn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ValidateEmployeesImpl implements ValidateEmployees {

    private final ExistEmployeesByIdIn existEmployeeByIdIn;

    @Override
    public void handle(ValidateEmployeesCommand command) {
        if (!existEmployeeByIdIn.existByIdIn(command.ids())) {
            throw new InvalidEmployeesException(command.ids());
        }
    }
}
