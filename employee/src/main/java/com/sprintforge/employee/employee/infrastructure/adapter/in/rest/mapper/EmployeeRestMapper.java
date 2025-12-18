package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmployeesQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByCuiQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByEmailQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByIdQuery;
import com.sprintforge.employee.employee.application.port.in.command.HireEmployeeCommand;
import com.sprintforge.employee.employee.application.port.in.command.UpdateEmployeeDetailCommand;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeePosition;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.HireEmployeeRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.UpdateEmployeeDetailRequestDTO;
import lombok.experimental.UtilityClass;

import java.util.UUID;

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
                request.profileImage(),
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
                request.igssPercentage(),
                request.irtraPercentage(),
                request.profileImage()
        );
    }
}
