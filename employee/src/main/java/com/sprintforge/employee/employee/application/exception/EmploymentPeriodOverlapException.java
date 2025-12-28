package com.sprintforge.employee.employee.application.exception;

import java.time.LocalDate;

import com.sprintforge.common.domain.exception.ValidationException;

public class EmploymentPeriodOverlapException extends ValidationException {
    public EmploymentPeriodOverlapException(LocalDate activePeriodStartDate) {
        super("El período activo comienza en: " + activePeriodStartDate + ", no se puede crear un nuevo período que se superponga.");
    }
    
}
