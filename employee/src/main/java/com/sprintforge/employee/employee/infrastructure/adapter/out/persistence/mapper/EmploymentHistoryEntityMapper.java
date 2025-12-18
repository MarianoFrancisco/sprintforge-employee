package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmploymentHistoryEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmploymentHistoryEntityMapper {
    public EmploymentHistory toDomain(EmploymentHistoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return EmploymentHistory.rehydrate(
                entity.getId(),
                EmployeeEntityMapper.toDomain(entity.getEmployee()),
                entity.getType(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getSalary(),
                entity.getNotes()
        );
    }

    public EmploymentHistoryEntity toEntity(EmploymentHistory domain) {
        if (domain == null) {
            return null;
        }
        return EmploymentHistoryEntity.builder()
                .id(domain.getId().value())
                .employee(EmployeeEntityMapper.toEntity(domain.getEmployee()))
                .type(domain.getType())
                .startDate(domain.getPeriod().getStartDate())
                .endDate(domain.getPeriod().getEndDate())
                .salary(domain.getSalary().amount())
                .notes(domain.getNotes().value())
                .build();
    }
}
