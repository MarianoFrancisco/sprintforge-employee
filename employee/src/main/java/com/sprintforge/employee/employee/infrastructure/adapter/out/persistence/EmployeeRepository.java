package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmployeesQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.*;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper.EmployeeEntityMapper;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository.EmployeeJpaRepository;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.specification.EmployeeSpecs;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@NullMarked
@Repository
@RequiredArgsConstructor
public class EmployeeRepository implements
        ExistEmployeesByIdIn,
        ExistsEmployeeByCui,
        ExistsEmployeeByEmail,
        FindAllEmployees,
        FindEmployeesByIdIn,
        FindEmployeeByCui,
        FindEmployeeByEmail,
        FindEmployeeById,
        SaveEmployee {

    private final EmployeeJpaRepository employeeJpaRepository;

    @Override
    public boolean existsByCui(String cui) {
        return employeeJpaRepository.existsByCui(cui);
    }

    @Override
    public boolean existsByEmail(String email) {
        return employeeJpaRepository.existsByEmail(email);
    }

    @Override
    public List<Employee> findAll(GetAllEmployeesQuery query) {
        Specification<EmployeeEntity> spec = EmployeeSpecs.withFilters(
                query.searchTerm(),
                query.position(),
                query.workloadType(),
                query.status()
        );

        return employeeJpaRepository.findAll(spec)
                .stream()
                .map(EmployeeEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Employee> findByCui(String cui) {
        return employeeJpaRepository.findByCui(cui).map(
                EmployeeEntityMapper::toDomain
        );
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return employeeJpaRepository.findByEmail(email).map(
                EmployeeEntityMapper::toDomain
        );
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return employeeJpaRepository.findById(id).map(
                EmployeeEntityMapper::toDomain
        );
    }

    @Override
    public Employee save(Employee employee) {
        EmployeeEntity entity = EmployeeEntityMapper.toEntity(employee);
        EmployeeEntity savedEntity = employeeJpaRepository.save(entity);
        return EmployeeEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existByIdIn(Set<UUID> ids) {
        return employeeJpaRepository.existsByIdIn(ids);
    }

    @Override
    public Set<Employee> findByIdIn(Set<UUID> ids) {
        return employeeJpaRepository.findByIdIn(ids).stream()
                .map(EmployeeEntityMapper::toDomain)
                .collect(toSet());
    }
}
