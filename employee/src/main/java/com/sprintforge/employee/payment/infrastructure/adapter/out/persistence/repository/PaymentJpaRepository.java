package com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;

public interface PaymentJpaRepository extends
        JpaRepository<PaymentEntity, UUID>,
        JpaSpecificationExecutor<PaymentEntity> {
    boolean existsByEmployeeAndDate(EmployeeEntity employee, LocalDate date);
    
}
