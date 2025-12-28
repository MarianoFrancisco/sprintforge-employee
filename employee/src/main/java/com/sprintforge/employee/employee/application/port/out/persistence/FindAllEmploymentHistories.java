package com.sprintforge.employee.employee.application.port.out.persistence;

import java.util.List;

import com.sprintforge.employee.employee.application.port.in.query.GetAllEmploymentHistoriesQuery;
import com.sprintforge.employee.employee.domain.EmploymentHistory;

public interface FindAllEmploymentHistories {
    List<EmploymentHistory> findAll(GetAllEmploymentHistoriesQuery query);
}
