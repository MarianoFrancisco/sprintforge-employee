package com.sprintforge.employee.position.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.entity.PositionEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface PositionJpaRepository extends
        JpaRepository<PositionEntity, UUID>,
        JpaSpecificationExecutor<PositionEntity> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);

    Optional<PositionEntity> findById(UUID id);
}
