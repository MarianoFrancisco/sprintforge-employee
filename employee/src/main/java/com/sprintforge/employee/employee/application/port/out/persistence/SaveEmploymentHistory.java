package com.sprintforge.employee.employee.application.port.out.persistence;

import com.sprintforge.employee.employee.domain.EmploymentHistory;

public interface SaveEmploymentHistory {
    EmploymentHistory save(EmploymentHistory employmentHistory);
}
