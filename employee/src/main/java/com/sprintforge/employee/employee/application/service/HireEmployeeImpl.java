package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.mapper.EmployeeMapper;
import com.sprintforge.employee.employee.application.mapper.EmploymentHistoryMapper;
import com.sprintforge.employee.employee.application.port.in.command.HireEmployee;
import com.sprintforge.employee.employee.application.port.in.command.HireEmployeeCommand;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmployee;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmploymentHistory;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class HireEmployeeImpl implements HireEmployee {

    private final SaveEmployee saveEmployee;
    private final SaveEmploymentHistory saveEmploymentHistory;

    @Override
    public Employee handle(HireEmployeeCommand command) {
        Employee employee = EmployeeMapper.toDomain(command);
        Employee employeeSaved = saveEmployee.save(employee);
        EmploymentHistory employmentHistory = EmploymentHistoryMapper.toDomain(command, employeeSaved);
        saveEmploymentHistory.save(employmentHistory);
        return null;
    }
}