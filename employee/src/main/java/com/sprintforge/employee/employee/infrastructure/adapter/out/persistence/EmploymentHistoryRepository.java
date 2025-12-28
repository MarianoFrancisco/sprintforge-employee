package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistoriesQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindActiveEmploymentByEmployee;
import com.sprintforge.employee.employee.application.port.out.persistence.FindAllEmploymentHistories;
import com.sprintforge.employee.employee.application.port.out.persistence.FindEmploymentByType;
import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmploymentHistory;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmploymentHistoryEntity;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper.EmployeeEntityMapper;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper.EmploymentHistoryEntityMapper;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository.EmploymentHistoryJpaRepository;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.specification.EmploymentHistorySpecs;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmploymentHistoryRepository implements
        SaveEmploymentHistory,
        FindActiveEmploymentByEmployee,
        FindEmploymentByType,
        FindAllEmploymentHistories {

    private final EmploymentHistoryJpaRepository employmentHistoryJpaRepository;

    @Override
    public EmploymentHistory save(EmploymentHistory employmentHistory) {

        EmploymentHistoryEntity entity = EmploymentHistoryEntityMapper.toEntity(employmentHistory);
        EmploymentHistoryEntity savedEntity = employmentHistoryJpaRepository.save(entity);
        return EmploymentHistoryEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<EmploymentHistory> findActiveEmploymentPeriodByEmployee(Employee employee) {
        return employmentHistoryJpaRepository
                .findByEmployeeAndEndDate(EmployeeEntityMapper.toEntity(employee), null)
                .map(EmploymentHistoryEntityMapper::toDomain);
    }

    @Override
    public List<EmploymentHistory> findAll(GetAllEmploymentHistoriesQuery query) {
        Specification<EmploymentHistoryEntity> spec = EmploymentHistorySpecs.withFilters(
                query.employee(),
                query.position(),
                query.type(),
                query.startDateFrom(),
                query.startDateTo()
        );
        return employmentHistoryJpaRepository.findAll(spec).stream()
                .map(EmploymentHistoryEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<EmploymentHistory> findByEmployeeAndType(Employee employee, EmploymentHistoryType type) {
        return employmentHistoryJpaRepository
                .findByEmployeeAndType(EmployeeEntityMapper.toEntity(employee), type)
                .stream()
                .findFirst()
                .map(EmploymentHistoryEntityMapper::toDomain);
    }
}
