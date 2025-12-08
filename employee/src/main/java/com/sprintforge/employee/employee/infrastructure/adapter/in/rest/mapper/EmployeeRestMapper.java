package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.employee.application.port.in.employee.activate.ActivateEmployeeCommand;
import com.sprintforge.employee.employee.application.port.in.employee.deactivate.DeactivateEmployeeCommand;
import com.sprintforge.employee.employee.application.port.in.employee.delete.DeleteEmployeeCommand;
import com.sprintforge.employee.employee.application.port.in.employee.getall.GetAllEmployeesQuery;
import com.sprintforge.employee.employee.application.port.in.employee.getbycui.GetEmployeeByCuiQuery;
import com.sprintforge.employee.employee.application.port.in.employee.getbyemail.GetEmployeeByEmailQuery;
import com.sprintforge.employee.employee.application.port.in.employee.getbyid.GetEmployeeByIdQuery;
import com.sprintforge.employee.employee.application.port.in.employee.hire.HireEmployeeCommand;
import com.sprintforge.employee.employee.application.port.in.employee.update.UpdateEmployeeDetailCommand;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.HireEmployeeRequestDTO;
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
                employee.getFullName().value(),
                employee.getPhoneNumber().value(),
                employee.getBirthDate().value(),
                employee.getPositionId().value(),
                employee.getWorkloadType(),
                employee.getSalary().value(),
                employee.getIgssPercentage().value(),
                employee.getIrtraPercentage().value(),
                employee.getProfileImage().value(),
                employee.getIsActive(),
                employee.getIsDeleted(),
                employee.getCreatedAt(),
                employee.getUpdatedAt(),
                employee.getPosition()
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
                request.igssPercentage(),
                request.irtraPercentage(),
                request.profileImage(),
                request.startDate(),
                request.notes()
        );
    }

    public UpdateEmployeeDetailCommand toUpdateCommand(
            UUID id,
            HireEmployeeRequestDTO request
    ) {
        return new UpdateEmployeeDetailCommand(
                id,
                request.firstName(),
                request.lastName(),
                request.phoneNumber(),
                request.birthDate(),
                request.positionId(),
                request.workloadType(),
                request.salary(),
                request.igssPercentage(),
                request.irtraPercentage(),
                request.profileImage()
        );
    }

    public ActivateEmployeeCommand toActivateCommand(
            UUID id
    ) {
        return new ActivateEmployeeCommand(id);
    }

    public DeactivateEmployeeCommand toDeactivateCommand(
            UUID id
    ) {
        return new DeactivateEmployeeCommand(id);
    }

    public DeleteEmployeeCommand toDeleteCommand(
            UUID id
    ) {
        return new DeleteEmployeeCommand(id);
    }
}
