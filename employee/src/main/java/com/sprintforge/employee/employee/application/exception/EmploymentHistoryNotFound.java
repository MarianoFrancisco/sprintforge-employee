package com.sprintforge.employee.employee.application.exception;

import com.sprintforge.common.application.exception.EntityNotFoundException;

public class EmploymentHistoryNotFound extends EntityNotFoundException {
    public EmploymentHistoryNotFound(String cui) {
        super("No existe historial laboral para el empleado con CUI: " + cui);
    }
    
}
