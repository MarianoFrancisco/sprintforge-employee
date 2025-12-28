package com.sprintforge.employee.employee.application.service;

import com.sprintforge.employee.employee.application.exception.DuplicateEmployeeException;
import com.sprintforge.employee.employee.application.mapper.EmployeeIntegrationMapper;
import com.sprintforge.employee.employee.application.mapper.EmployeeMapper;
import com.sprintforge.employee.employee.application.port.in.command.HireEmployee;
import com.sprintforge.employee.employee.application.port.in.command.HireEmployeeCommand;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeEventPublisher;
import com.sprintforge.employee.employee.application.port.out.persistence.ExistsEmployeeByCui;
import com.sprintforge.employee.employee.application.port.out.persistence.ExistsEmployeeByEmail;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmployee;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmploymentHistory;
import com.sprintforge.employee.employee.application.port.out.storage.EmployeeImageStorage;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.position.application.exception.PositionNotFoundException;
import com.sprintforge.employee.position.application.port.out.persistence.FindPositionById;
import com.sprintforge.employee.position.domain.Position;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class HireEmployeeImpl implements HireEmployee {

    private final ExistsEmployeeByCui existsEmployeeByCui;
    private final ExistsEmployeeByEmail existsEmployeeByEmail;
    private final FindPositionById findPositionById;
    private final SaveEmployee saveEmployee;
    private final SaveEmploymentHistory saveEmploymentHistory;
    private final EmployeeEventPublisher employeeEventPublisher;
    private final EmployeeImageStorage employeeImageStorage;

    @Override
    public Employee handle(HireEmployeeCommand command) {
        if (existsEmployeeByCui.existsByCui(command.cui())) {
            throw DuplicateEmployeeException.byCui(command.cui());
        }
        if (existsEmployeeByEmail.existsByEmail(command.email())) {
            throw DuplicateEmployeeException.byEmail(command.email());
        }

        Position position = findPositionById.findById(command.positionId())
                .orElseThrow(() -> new PositionNotFoundException(command.positionId().toString()));
        Employee employee = EmployeeMapper.toDomain(command, position);

        command.profileImage().ifPresent(
                (image) -> employee.setProfileImage(
                        employeeImageStorage.uploadEmployeeImage(image).value()));

        Employee employeeSaved = saveEmployee.save(employee);

        EmploymentHistory employmentHistory = EmploymentHistory.create(
                employeeSaved,
                EmploymentHistoryType.HIRING,
                command.startDate(),
                command.salary(),
                command.notes());

        saveEmploymentHistory.save(employmentHistory);
        employeeEventPublisher.publishEmployeeCreated(
                EmployeeIntegrationMapper.from(employeeSaved)
        );
        return employeeSaved;
    }
}
