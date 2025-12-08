package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.employee.deactivate.DeactivateEmployee;
import com.sprintforge.employee.employee.application.port.in.employee.deactivate.DeactivateEmployeeCommand;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeById;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmployee;
import com.sprintforge.employee.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DeactivateEmployeeImpl implements DeactivateEmployee {

    private final FindEmployeeById findEmployeeById;
    private final SaveEmployee saveEmployee;

    @Override
    public Employee handle(DeactivateEmployeeCommand command) {
        Employee employee = findEmployeeById.findById(command.id())
                .orElseThrow(() ->
                        new EmployeeNotFoundException("identificador", command.id().toString())
                );
        employee.deactivate();
        Employee employeeSaved = saveEmployee.save(employee);
        return employeeSaved;
    }
}