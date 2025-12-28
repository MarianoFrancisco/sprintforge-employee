package com.sprintforge.employee.employee.domain.valueobject;

import java.time.LocalDate;

import com.sprintforge.common.domain.exception.ValidationException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"startDate", "endDate"})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmploymentHistoryPeriod {
    private final LocalDate startDate;
    private LocalDate endDate;

    public static EmploymentHistoryPeriod start(LocalDate startDate) {

        return new EmploymentHistoryPeriod(validateStartDate(startDate), null);
    }

    public static EmploymentHistoryPeriod of(LocalDate startDate, LocalDate endDate) {
        return new EmploymentHistoryPeriod(validateStartDate(startDate), validateEndDate(startDate, endDate));
    }

    public void end(LocalDate endDate) {
        if (this.endDate != null) {
            throw new ValidationException("El período de empleo ya ha finalizado");
            
        }

        validateEndDate(this.startDate, endDate);
        this.endDate = endDate;
    }

    public boolean isActive() {
        return this.endDate == null;
    }


    private static LocalDate validateStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new ValidationException("La fecha de inicio no puede ser nula");
        }

        if (startDate.isAfter(LocalDate.now())) {
            throw new ValidationException("La fecha de inicio no puede ser futura");
        }
        return startDate;
    }

    private static LocalDate validateEndDate(LocalDate startDate, LocalDate endDate) {
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new ValidationException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        return endDate;
    }
}
