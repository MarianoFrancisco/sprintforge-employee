package com.sprintforge.employee.position.application.service;

import com.sprintforge.employee.position.application.port.in.query.GetAllPositions;
import com.sprintforge.employee.position.application.port.in.query.GetAllPositionsQuery;
import com.sprintforge.employee.position.application.port.out.persistence.FindAllPositions;
import com.sprintforge.employee.position.domain.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class GetAllPositionsImpl implements GetAllPositions {

    private final FindAllPositions findAllPositions;

    @Override
    public List<Position> handle(GetAllPositionsQuery query) {
        return findAllPositions.findAll(query);
    }
}