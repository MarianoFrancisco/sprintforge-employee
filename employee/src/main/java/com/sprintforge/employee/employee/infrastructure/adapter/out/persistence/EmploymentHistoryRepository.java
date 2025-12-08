package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.employee.application.port.out.persistence.SaveEmploymentHistory;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmploymentHistoryEntity;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper.EmploymentHistoryEntityMapper;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository.EmploymentHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmploymentHistoryRepository implements
        SaveEmploymentHistory {

    private final EmploymentHistoryJpaRepository employmentHistoryJpaRepository;

    @Override
    public EmploymentHistory save(EmploymentHistory employmentHistory) {

        EmploymentHistoryEntity entity = EmploymentHistoryEntityMapper.toEntity(employmentHistory);
        EmploymentHistoryEntity savedEntity = employmentHistoryJpaRepository.save(entity);
        return EmploymentHistoryEntityMapper.toDomain(savedEntity);
    }
}
