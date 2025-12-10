package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.port.in.command.UpdateEmployeeDetail;
import com.sprintforge.employee.employee.application.port.in.command.UpdateEmployeeDetailCommand;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeById;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmployee;
import com.sprintforge.employee.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateEmployeeDetailImpl implements UpdateEmployeeDetail {

    private final FindEmployeeById findById;
    private final SaveEmployee saveEmployee;

    @Override
    public Employee handle(UpdateEmployeeDetailCommand command) {
        Employee employee = findById.findById(command.id())
                .orElseThrow(() ->
                        new EmployeeNotFoundException("identificador", command.id().toString())
                );

        employee.updateDetails(
                command.firstName(),
                command.lastName(),
                command.phoneNumber(),
                command.birthDate(),
                command.positionId(),
                command.igssPercentage(),
                command.irtraPercentage(),
                null//command.profileImage()
        );

        Employee employeeSaved = saveEmployee.save(employee);
        return employeeSaved;
    }
}