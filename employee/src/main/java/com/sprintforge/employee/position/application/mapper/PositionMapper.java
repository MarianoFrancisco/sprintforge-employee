package com.sprintforge.employee.position.application.mapper;

import com.sprintforge.employee.position.application.port.in.command.CreatePositionCommand;
import com.sprintforge.employee.position.domain.Position;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PositionMapper {

    public Position toDomain(CreatePositionCommand command) {
        return new Position(
                command.name(),
                command.description()
        );
    }
}
