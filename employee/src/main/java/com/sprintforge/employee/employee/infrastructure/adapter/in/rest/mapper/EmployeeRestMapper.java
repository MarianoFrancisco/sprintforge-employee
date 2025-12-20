package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.employee.application.port.in.query.*;
import com.sprintforge.employee.employee.application.port.in.command.*;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeePosition;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.*;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class EmployeeRestMapper {

    public GetAllEmployeesQuery toQuery(String searchTerm, Boolean isActive, Boolean isDeleted) {
        return new GetAllEmployeesQuery(
                searchTerm,
                isActive,
                isDeleted
        );
    }

    public EmployeeResponseDTO toResponse(Employee employee) {
        return new EmployeeResponseDTO(
                employee.getId().value(),
                employee.getCui().value(),
                employee.getEmail().value(),
                employee.getFirstName().value(),
                employee.getLastName().value(),
                employee.getFullName(),
                employee.getPhoneNumber().value(),
                employee.getBirthDate().value(),
                employee.getWorkloadType(),
                employee.getSalary().amount(),
                employee.getProfileImage().value(),
                employee.getStatus(),
                new EmployeePosition(
                        employee.getPosition().getId().value(),
                        employee.getPosition().getName().value(),
                        employee.getPosition().getDescription().value()
                )
        );
    }

    public GetEmployeeByCuiQuery toQueryByCui(String cui) {
        return new GetEmployeeByCuiQuery(cui);
    }

    public GetEmployeeByEmailQuery toQueryByEmail(String email) {
        return new GetEmployeeByEmailQuery(email);
    }

    public GetEmployeeByIdQuery toQueryById(UUID id) {
        return new GetEmployeeByIdQuery(id);
    }

    public HireEmployeeCommand toHireCommand(
            HireEmployeeRequestDTO request
    ) {
        return new HireEmployeeCommand(
                request.cui(),
                request.email(),
                request.firstName(),
                request.lastName(),
                request.phoneNumber(),
                request.birthDate(),
                request.positionId(),
                request.workloadType(),
                request.salary(),
                toImageContent(request.profileImage()),
                request.startDate(),
                request.notes()
        );
    }

    public UpdateEmployeeDetailCommand toUpdateCommand(
            UUID id,
            UpdateEmployeeDetailRequestDTO request
    ) {
        return new UpdateEmployeeDetailCommand(
                id,
                request.firstName(),
                request.lastName(),
                request.phoneNumber(),
                request.birthDate(),
                toImageContent(request.profileImage())
        );
    }

    public IncreaseEmployeeSalaryCommand toIncreaseSalaryCommand(
            String cui,
            IncreaseEmployeeSalaryRequestDTO request
    ) {
        return new IncreaseEmployeeSalaryCommand(
                cui,
                request.increaseAmount(),
                request.date(),
                request.notes()
        );
    }

    public ReinstateEmployeeCommand toReinstateCommand(
            String cui,
            ReinstateEmployeeRequestDTO request
    ) {
        return new ReinstateEmployeeCommand(
                cui,
                request.date(),
                request.notes()
        );
    }

    public SuspendEmployeeCommand toSuspendCommand(
            String cui,
            SuspendEmployeeRequestDTO request
    ) {
        return new SuspendEmployeeCommand(
                cui,
                request.date(),
                request.notes()
        );
    }

    public TerminateEmployeeCommand toTerminateCommand(
            String cui,
            TerminateEmployeeRequestDTO request
    ) {
        return new TerminateEmployeeCommand(
                cui,
                request.date(),
                request.notes()
        );
    }

    private Optional<ImageContent> toImageContent(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(new ImageContent(
                    multipartFile.getInputStream(),
                    multipartFile.getSize(),
                    multipartFile.getContentType()
            ));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
