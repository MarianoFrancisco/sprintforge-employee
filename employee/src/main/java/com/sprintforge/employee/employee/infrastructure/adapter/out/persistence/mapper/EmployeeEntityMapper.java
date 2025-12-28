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
        Employee employee = Employee.rehydrate(
                entity.getId(),
                entity.getCui(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getFullName(),
                entity.getPhoneNumber(),
                entity.getBirthDate(),
                PositionEntityMapper.toDomain(entity.getPosition()),
                entity.getWorkloadType(),
                entity.getSalary(),
                entity.getProfileImage(),
                entity.getStatus()
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
                .fullName(domain.getFullName())
                .phoneNumber(domain.getPhoneNumber().value())
                .birthDate(domain.getBirthDate().value())
                .position(PositionEntityMapper.toEntity(domain.getPosition()))
                .workloadType(domain.getWorkloadType())
                .salary(domain.getSalary().amount())
                .profileImage(domain.getProfileImage().value())
                .status(domain.getStatus())
                .build();
    }
}
