package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeEntityMapper {
    public Employee toDomain(EmployeeEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Employee(
                entity.getId(),
                entity.getCui(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhoneNumber(),
                entity.getBirthDate(),
                entity.getPositionId(),
                entity.getWorkloadType().name(),
                entity.getSalary(),
                entity.getIgssPercentage(),
                entity.getIrtraPercentage(),
                entity.getProfileImage(),
                entity.getIsActive(),
                entity.getIsDeleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public EmployeeEntity toEntity(Employee domain) {
        if (domain == null) {
            return null;
        }
        return EmployeeEntity.builder()
                .id(domain.getId().value())
                .cui(domain.getCui().value())
                .email(domain.getEmail().value())
                .firstName(domain.getFirstName().value())
                .lastName(domain.getLastName().value())
                .phoneNumber(domain.getPhoneNumber().value())
                .birthDate(domain.getBirthDate().value())
                .positionId(domain.getPositionId().value())
                .workloadType(domain.getWorkloadType())
                .salary(domain.getSalary().value())
                .igssPercentage(domain.getIgssPercentage().value())
                .irtraPercentage(domain.getIrtraPercentage().value())
                .profileImage(domain.getProfileImage().value())
                .isActive(domain.getIsActive())
                .isDeleted(domain.getIsDeleted())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
