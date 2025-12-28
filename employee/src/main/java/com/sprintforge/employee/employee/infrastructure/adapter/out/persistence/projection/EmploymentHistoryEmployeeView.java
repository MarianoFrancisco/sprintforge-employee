package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.projection;

import java.time.LocalDate;
import java.util.UUID;

public interface EmploymentHistoryEmployeeView {

    UUID getEmployeeId();

    String getCui();

    String getFullName();

    LocalDate getDate();
}
