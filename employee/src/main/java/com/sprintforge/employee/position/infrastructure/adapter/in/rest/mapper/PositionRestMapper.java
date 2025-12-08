package com.sprintforge.employee.position.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.position.application.port.in.command.ActivatePositionCommand;
import com.sprintforge.employee.position.application.port.in.command.CreatePositionCommand;
import com.sprintforge.employee.position.application.port.in.command.DeactivatePositionCommand;
import com.sprintforge.employee.position.application.port.in.command.DeletePositionCommand;
import com.sprintforge.employee.position.application.port.in.query.GetAllPositionsQuery;
import com.sprintforge.employee.position.application.port.in.query.GetPositionByIdQuery;
import com.sprintforge.employee.position.application.port.in.command.UpdatePositionDetailCommand;
import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.CreatePositionRequestDTO;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.PositionResponseDTO;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.UpdatePositionDetailRequestDTO;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class PositionRestMapper {

    public GetAllPositionsQuery toQuery(String searchTerm, Boolean isActive, Boolean isDeleted) {
        return new GetAllPositionsQuery(searchTerm, isActive, isDeleted);
    }

    public GetPositionByIdQuery toQuery(UUID id) {
        return new GetPositionByIdQuery(id);
    }

    public CreatePositionCommand toCreateCommand(CreatePositionRequestDTO request) {
        return new CreatePositionCommand(
                request.name(),
                request.description()
        );
    }

    public PositionResponseDTO toResponse(Position position) {
        return new PositionResponseDTO(
                position.getId().value(),
                position.getName().value(),
                position.getDescription().value(),
                position.getIsActive(),
                position.getIsDeleted(),
                position.getCreatedAt(),
                position.getUpdatedAt()
        );
    }

    public UpdatePositionDetailCommand toUpdateCommand(UUID id, UpdatePositionDetailRequestDTO request) {
        return new UpdatePositionDetailCommand(
                id,
                request.name(),
                request.description()
        );
    }

    public ActivatePositionCommand toActivateCommand(
            UUID id
    ) {
        return new ActivatePositionCommand(id);
    }

    public DeactivatePositionCommand toDeactivateCommand(
            UUID id
    ) {
        return new DeactivatePositionCommand(id);
    }

    public DeletePositionCommand toDeleteCommand(
            UUID id
    ) {
        return new DeletePositionCommand(id);
    }
}
