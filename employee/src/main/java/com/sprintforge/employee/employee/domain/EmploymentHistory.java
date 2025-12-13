package com.sprintforge.employee.employee.domain;

import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryId;
import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeId;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeSalary;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryNotes;
import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryEmployee;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Getter
public class EmploymentHistory {

    private final EmploymentHistoryId id;
    private final EmployeeId employeeId;
    private final EmploymentHistoryType type;

    private final LocalDate startDate;
    private LocalDate endDate;

    private final EmployeeSalary salary;
    private final EmploymentHistoryNotes notes;

    private final Instant createdAt;
    private final Instant updatedAt;
    @Setter
    private EmploymentHistoryEmployee employee;

    public EmploymentHistory(
            UUID employeeId,
            EmploymentHistoryType type,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal salary,
            String notes
    ) {
        Instant timestamp = now();

        this.id = new EmploymentHistoryId(randomUUID());
        this.employeeId = new EmployeeId(employeeId);
        this.type = type;
        this.startDate = validateStartDate(startDate);
        validateEndDate(this.startDate, endDate);
        this.endDate = endDate;
        this.salary = new EmployeeSalary(salary);
        this.notes = new EmploymentHistoryNotes(notes);
        this.createdAt = timestamp;
        this.updatedAt = timestamp;
    }

    public EmploymentHistory(
            UUID id,
            UUID employeeId,
            String type,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal salary,
            String notes,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = new EmploymentHistoryId(id);
        this.employeeId = new EmployeeId(employeeId);
        this.type = EmploymentHistoryType.safeValueOf(type);
        this.startDate = validateStartDate(startDate);
        validateEndDate(this.startDate, endDate);
        this.endDate = endDate;
        this.salary = new EmployeeSalary(salary);
        this.notes = new EmploymentHistoryNotes(notes);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private LocalDate validateStartDate(LocalDate startDate) {
        Objects.requireNonNull(startDate, "La fecha de inicio no puede estar vacía");
        if (startDate.isAfter(LocalDate.now())) {
            throw new ValidationException("La fecha de inicio no puede ser futura");
        }
        return startDate;
    }

    private void validateEndDate(LocalDate startDate, LocalDate endDate) {
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new ValidationException(
                    "La fecha de fin no puede ser anterior a la fecha de inicio"
            );
        }
    }
}
