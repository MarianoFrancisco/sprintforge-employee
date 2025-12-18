package com.sprintforge.employee.employee.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.sprintforge.employee.employee.domain.valueobject.*;

import lombok.*;

@Getter
@EqualsAndHashCode(of = { "employee", "type", "period" })
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmploymentHistory {

    private final EmploymentHistoryId id;
    @NonNull
    private final Employee employee;
    @NonNull
    private final EmploymentHistoryType type;
    private final EmploymentHistoryPeriod period;
    private final EmployeeSalary salary;
    private final EmploymentHistoryNotes notes;

    public static EmploymentHistory create(
            Employee employee,
            EmploymentHistoryType type,
            LocalDate startDate,
            BigDecimal salary,
            String notes) {
        return new EmploymentHistory(
                new EmploymentHistoryId(UUID.randomUUID()),
                employee,
                type,
                EmploymentHistoryPeriod.start(startDate),
                new EmployeeSalary(salary),
                new EmploymentHistoryNotes(notes));
    }

    public static EmploymentHistory rehydrate(
            UUID id,
            Employee employee,
            EmploymentHistoryType type,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal salary,
            String notes) {
        return new EmploymentHistory(
                new EmploymentHistoryId(id),
                employee,
                type,
                EmploymentHistoryPeriod.of(startDate, endDate),
                new EmployeeSalary(salary),
                new EmploymentHistoryNotes(notes));
    }

    public void close(LocalDate endDate) {
        this.period.end(endDate);
    }

    public boolean isActive() {
        return period.isActive();
    }
}
