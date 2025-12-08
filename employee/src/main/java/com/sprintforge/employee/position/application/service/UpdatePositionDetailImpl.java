package com.sprintforge.employee.position.application.service;

import com.sprintforge.employee.position.application.exception.DuplicatePositionException;
import com.sprintforge.employee.position.application.exception.PositionNotFoundException;
import com.sprintforge.employee.position.application.port.in.command.UpdatePositionDetail;
import com.sprintforge.employee.position.application.port.in.command.UpdatePositionDetailCommand;
import com.sprintforge.employee.position.application.port.out.persistence.ExistsPositionByNameAndIdNot;
import com.sprintforge.employee.position.application.port.out.persistence.FindPositionById;
import com.sprintforge.employee.position.application.port.out.persistence.SavePosition;
import com.sprintforge.employee.position.domain.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdatePositionDetailImpl implements UpdatePositionDetail {

    private final ExistsPositionByNameAndIdNot existsPositionByNameAndIdNot;
    private final FindPositionById findById;
    private final SavePosition savePosition;

    @Override
    public Position handle(UpdatePositionDetailCommand command) {
        Position position = findById.findById(command.id())
                .orElseThrow(() ->
                        new PositionNotFoundException(command.id().toString())
                );

        if (!position.getName().value().equals(command.name())
                && existsPositionByNameAndIdNot.existsByNameAndIdNot(command.name(), command.id())) {
            throw new DuplicatePositionException(command.name());
        }

        position.updateDetails(
                command.name(),
                command.description()
        );

        Position positionSaved = savePosition.save(position);
        return positionSaved;
    }
}