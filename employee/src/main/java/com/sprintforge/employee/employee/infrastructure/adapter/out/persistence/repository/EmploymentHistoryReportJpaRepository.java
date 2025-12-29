package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmploymentHistoryEntity;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.projection.EmploymentHistoryEmployeeView;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NullMarked
public interface EmploymentHistoryReportJpaRepository extends
        JpaRepository<EmploymentHistoryEntity, UUID> {

    @Query(value = """
            SELECT
                e.id        AS employeeId,
                e.cui       AS cui,
                e.full_name AS fullName,
                eh.start_date AS date
            FROM employment_history eh
            JOIN employee e ON e.id = eh.employee_id
            WHERE eh.type = 'HIRING'
              AND eh.start_date >= COALESCE(?1, eh.start_date)
              AND eh.start_date <= COALESCE(?2, eh.start_date)
            ORDER BY eh.start_date, e.cui
            """, nativeQuery = true)
    List<EmploymentHistoryEmployeeView> hiredEmployees(
            LocalDate from,
            LocalDate to
    );

    @Query(value = """
            SELECT
                e.id        AS employeeId,
                e.cui       AS cui,
                e.full_name AS fullName,
                eh.start_date AS date
            FROM employment_history eh
            JOIN employee e ON e.id = eh.employee_id
            WHERE eh.type = 'TERMINATION'
              AND eh.start_date >= COALESCE(?1, eh.start_date)
              AND eh.start_date <= COALESCE(?2, eh.start_date)
            ORDER BY eh.start_date, e.cui
            """, nativeQuery = true)
    List<EmploymentHistoryEmployeeView> terminatedEmployees(
            LocalDate from,
            LocalDate to
    );
}
