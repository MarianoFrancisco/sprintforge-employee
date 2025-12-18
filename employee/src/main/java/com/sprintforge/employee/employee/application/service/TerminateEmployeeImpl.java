package com.sprintforge.employee.employee.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprintforge.employee.employee.application.exception.EmployeeNotFoundException;
import com.sprintforge.employee.employee.application.exception.EmploymentHistoryNotFound;
import com.sprintforge.employee.employee.application.exception.EmploymentPeriodOverlapException;
import com.sprintforge.employee.employee.application.port.in.command.TerminateEmployee;
import com.sprintforge.employee.employee.application.port.in.command.TerminateEmployeeCommand;
import com.sprintforge.employee.employee.application.port.out.persistence.FindActiveEmploymentByEmployee;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmployeeByCui;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmployee;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmploymentHistory;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TerminateEmployeeImpl implements TerminateEmployee {
    private final FindEmployeeByCui findEmployeeByCui;
    private final FindActiveEmploymentByEmployee findActiveEmploymentByEmployee;
    private final SaveEmployee saveEmployee;
    private final SaveEmploymentHistory saveEmploymentHistory;

    @Override
    public Employee handle(TerminateEmployeeCommand command) {
        Employee employee = findEmployeeByCui.findByCui(command.cui())
                .orElseThrow(() -> EmployeeNotFoundException.byCui(command.cui()));

        EmploymentHistory activePeriod = findActiveEmploymentByEmployee.findActiveByEmployee(employee)
                .orElseThrow(() -> new EmploymentHistoryNotFound(command.cui()));

        if (!command.date().isAfter(activePeriod.getPeriod().getStartDate())) {
            throw new EmploymentPeriodOverlapException(activePeriod.getPeriod().getStartDate());
        }

        employee.terminate();
        saveEmployee.save(employee);

        activePeriod.close(command.date());
        saveEmploymentHistory.save(activePeriod);

        EmploymentHistory newPeriod = EmploymentHistory.create(
                employee,
                EmploymentHistoryType.TERMINATION,
                command.date(),
                employee.getSalary().amount(),
                command.notes());
        saveEmploymentHistory.save(newPeriod);
        return employee;
    }

}
