package com.sprintforge.employee.position.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.entity.PositionEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PositionEntityMapper {

    public Position toDomain(PositionEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Position(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIsActive(),
                entity.getIsDeleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public PositionEntity toEntity(Position domain) {
        if (domain == null) {
            return null;
        }

        return PositionEntity.builder()
                .id(domain.getId().value())
                .name(domain.getName().value())
                .description(domain.getDescription().value())
                .isActive(domain.getIsActive())
                .isDeleted(domain.getIsDeleted())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
