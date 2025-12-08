package com.sprintforge.employee.position.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.position.application.port.in.getall.GetAllPositionsQuery;
import com.sprintforge.employee.position.application.port.out.persistence.*;
import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.entity.PositionEntity;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.mapper.PositionEntityMapper;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.repository.PositionJpaRepository;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.specification.PositionSpecs;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NullMarked
@Repository
@RequiredArgsConstructor
public class PositionRepository implements
        ExistsPositionByName,
        ExistsPositionByNameAndIdNot,
        FindAllPositions,
        FindPositionById,
        SavePosition {

    private final PositionJpaRepository positionJpaRepository;

    @Override
    public boolean existsByNameAndIdNot(String name, UUID id) {
        return positionJpaRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public boolean existsByName(String name) {
        return positionJpaRepository.existsByName(name);
    }

    @Override
    public List<Position> findAll(GetAllPositionsQuery query) {
        Specification<PositionEntity> spec = PositionSpecs.withFilters(
                query.searchTerm(),
                query.isActive(),
                query.isDeleted()
        );

        return positionJpaRepository.findAll(spec)
                .stream()
                .map(PositionEntityMapper::toDomain)
                .toList();
    }


    @Override
    public Optional<Position> findById(UUID id) {
        return positionJpaRepository.findById(id).map(
                PositionEntityMapper::toDomain
        );
    }

    @Override
    public Position save(Position position) {
        PositionEntity entity = PositionEntityMapper.toEntity(position);
        PositionEntity savedEntity = positionJpaRepository.save(entity);
        return PositionEntityMapper.toDomain(savedEntity);
    }
}
