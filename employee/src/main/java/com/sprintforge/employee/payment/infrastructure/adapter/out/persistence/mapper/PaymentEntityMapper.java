package com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper.EmployeeEntityMapper;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.domain.valueobject.*;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentEntityMapper {

    public Payment toDomain(PaymentEntity entity) {
        if (entity == null) {
            return null;
        }

        return Payment.rehydrate(
                entity.getId(),
                EmployeeEntityMapper.toDomain(entity.getEmployee()),
                new PaymentDate(entity.getDate()),
                new Money(entity.getBaseSalary()),
                new Money(entity.getBonus()),
                new Money(entity.getDeduction()),
                new Money(entity.getTotal()),
                new PaymentNotes(entity.getNotes())
        );
    }

    public PaymentEntity toEntity(Payment domain) {
        if (domain == null) {
            return null;
        }
        return PaymentEntity.builder()
                .id(domain.getId().value())
                .employee(EmployeeEntityMapper.toEntity(domain.getEmployee()))
                .date(domain.getDate().value())
                .baseSalary(domain.getBaseSalary().amount())
                .bonus(domain.getBonus().amount())
                .deduction(domain.getDeduction().amount())
                .total(domain.getTotal().amount())
                .notes(domain.getNotes().value())
                .build();
    }
    
}
