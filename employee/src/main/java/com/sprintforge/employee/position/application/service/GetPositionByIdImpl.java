package com.sprintforge.employee.position.application.service;

import com.sprintforge.employee.position.application.exception.PositionNotFoundException;
import com.sprintforge.employee.position.application.port.in.query.GetPositionById;
import com.sprintforge.employee.position.application.port.in.query.GetPositionByIdQuery;
import com.sprintforge.employee.position.application.port.out.persistence.FindPositionById;
import com.sprintforge.employee.position.domain.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class GetPositionByIdImpl implements GetPositionById {

    private final FindPositionById findById;

    @Override
    public Position handle(GetPositionByIdQuery query) {
        return findById.findById(query.id())
                .orElseThrow(() ->
                        new PositionNotFoundException(query.id().toString())
                );
    }
}