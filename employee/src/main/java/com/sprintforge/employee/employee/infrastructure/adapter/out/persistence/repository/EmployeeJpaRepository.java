package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface EmployeeJpaRepository extends
        JpaRepository<EmployeeEntity, UUID>,
        JpaSpecificationExecutor<EmployeeEntity> {

    boolean existsByCui(String cui);

    boolean existsByEmail(String email);

    Optional<EmployeeEntity> findByCui(String cui);

    Optional<EmployeeEntity> findByEmail(String email);

    Optional<EmployeeEntity> findById(UUID id);
}
