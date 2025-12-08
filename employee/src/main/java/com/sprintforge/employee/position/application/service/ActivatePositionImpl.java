package com.sprintforge.employee.position.application.service;

import com.sprintforge.employee.position.application.exception.PositionNotFoundException;
import com.sprintforge.employee.position.application.port.in.activate.ActivatePosition;
import com.sprintforge.employee.position.application.port.in.activate.ActivatePositionCommand;
import com.sprintforge.employee.position.application.port.out.persistence.FindPositionById;
import com.sprintforge.employee.position.application.port.out.persistence.SavePosition;
import com.sprintforge.employee.position.domain.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ActivatePositionImpl implements ActivatePosition {

    private final FindPositionById findById;
    private final SavePosition savePosition;

    @Override
    public Position handle(ActivatePositionCommand command) {
        Position position = findById.findById(command.id())
                .orElseThrow(() ->
                        new PositionNotFoundException(command.id().toString())
                );
        position.activate();
        Position positionSaved = savePosition.save(position);
        return positionSaved;
    }
}