package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.command.ActivateEmployee;
import com.sprintforge.employee.employee.application.port.in.command.ActivateEmployeeCommand;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeById;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmployee;
import com.sprintforge.employee.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ActivateEmployeeImpl implements ActivateEmployee {

    private final FindEmployeeById findEmployeeById;
    private final SaveEmployee saveEmployee;

    @Override
    public Employee handle(ActivateEmployeeCommand command) {
        Employee employee = findEmployeeById.findById(command.id())
                .orElseThrow(() ->
                        new EmployeeNotFoundException("identificador", command.id().toString())
                );
        employee.activate();
        saveEmployee.save(employee);
        Employee employeeSaved = saveEmployee.save(employee);
        return employeeSaved;
    }
}