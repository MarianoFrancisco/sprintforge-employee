package com.sprintforge.employee.position.application.service;

import com.sprintforge.employee.position.application.exception.DuplicatePositionException;
import com.sprintforge.employee.position.application.mapper.PositionMapper;
import com.sprintforge.employee.position.application.port.in.create.CreatePosition;
import com.sprintforge.employee.position.application.port.in.create.CreatePositionCommand;
import com.sprintforge.employee.position.application.port.out.persistence.ExistsPositionByName;
import com.sprintforge.employee.position.application.port.out.persistence.SavePosition;
import com.sprintforge.employee.position.domain.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreatePositionImpl implements CreatePosition {

    private final ExistsPositionByName existsPositionByName;
    private final SavePosition savePosition;

    @Override
    public Position handle(CreatePositionCommand command) {
        if (existsPositionByName.existsByName(command.name())) {
            throw new DuplicatePositionException(command.name());
        }
        Position position = PositionMapper.toDomain(command);
        Position positionSaved = savePosition.save(position);
        return positionSaved;
    }
}