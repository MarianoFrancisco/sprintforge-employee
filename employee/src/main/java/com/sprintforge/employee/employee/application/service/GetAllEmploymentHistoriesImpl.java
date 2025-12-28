package com.sprintforge.employee.employee.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistories;
import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistoriesQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.FindAllEmploymentHistories;
import com.sprintforge.employee.employee.domain.EmploymentHistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllEmploymentHistoriesImpl implements GetAllEmploymentHistories {
    private final FindAllEmploymentHistories findAllEmploymentHistories;

    @Override
    public List<EmploymentHistory> handle(GetAllEmploymentHistoriesQuery query) {
        return findAllEmploymentHistories.findAll(query);    
    }
    
}
