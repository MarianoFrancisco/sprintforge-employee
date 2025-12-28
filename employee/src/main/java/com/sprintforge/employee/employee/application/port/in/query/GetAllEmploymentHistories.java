package com.sprintforge.employee.employee.application.port.in.query;

import java.util.List;

import com.sprintforge.employee.employee.domain.EmploymentHistory;

public interface GetAllEmploymentHistories {
    List<EmploymentHistory> handle(GetAllEmploymentHistoriesQuery query);
}
