package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.mapper.PositionEntityMapper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeEntityMapper {
    public Employee toDomain(EmployeeEntity entity) {
        if (entity == null) {
            return null;
        }
        Employee employee = new Employee(
                entity.getId(),
                entity.getCui(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getFullName(),
                entity.getPhoneNumber(),
                entity.getBirthDate(),
                PositionEntityMapper.toDomain(entity.getPosition()),
                entity.getWorkloadType().name(),
                entity.getSalary(),
                entity.getIgssPercentage(),
                entity.getIrtraPercentage(),
                entity.getProfileImage(),
                entity.isActive(),
                entity.isDeleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
        return employee;
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
                .fullName(domain.getFullName().value())
                .phoneNumber(domain.getPhoneNumber().value())
                .birthDate(domain.getBirthDate().value())
                .position(PositionEntityMapper.toEntity(domain.getPosition()))
                .workloadType(domain.getWorkloadType())
                .salary(domain.getSalary().value())
                .igssPercentage(domain.getIgssPercentage().value())
                .irtraPercentage(domain.getIrtraPercentage().value())
                .profileImage(domain.getProfileImage().value())
                .isActive(domain.isActive())
                .isDeleted(domain.isDeleted())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
