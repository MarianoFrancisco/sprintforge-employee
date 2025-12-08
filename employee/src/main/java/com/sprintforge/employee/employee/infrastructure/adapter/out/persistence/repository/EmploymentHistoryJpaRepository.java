package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmploymentHistoryEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

@NullMarked
public interface EmploymentHistoryJpaRepository extends
        JpaRepository<EmploymentHistoryEntity, UUID>,
        JpaSpecificationExecutor<EmploymentHistoryEntity> {

}
