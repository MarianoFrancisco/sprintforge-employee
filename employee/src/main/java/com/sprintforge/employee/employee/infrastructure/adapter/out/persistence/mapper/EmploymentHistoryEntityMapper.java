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
        return new EmploymentHistory(
                entity.getId(),
                entity.getEmployeeId(),
                entity.getType().name(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getSalary(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public EmploymentHistoryEntity toEntity(EmploymentHistory domain) {
        if (domain == null) {
            return null;
        }
        return EmploymentHistoryEntity.builder()
                .id(domain.getId().value())
                .employeeId(domain.getEmployeeId().value())
                .type(domain.getType())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .salary(domain.getSalary().value())
                .notes(domain.getNotes().value())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
