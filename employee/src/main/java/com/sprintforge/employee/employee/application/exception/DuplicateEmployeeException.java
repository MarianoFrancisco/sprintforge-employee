package com.sprintforge.employee.employee.application.exception;

import com.sprintforge.common.application.exception.DuplicateEntityException;

public class DuplicateEmployeeException extends DuplicateEntityException {

    private DuplicateEmployeeException(String field, String value) {
        super(String.format("El empleado con %s \"%s\" ya existe.", field, value));
    }

    public static DuplicateEmployeeException byCui(String cui) {
        return new DuplicateEmployeeException("el CUI", cui);
    }

    public static DuplicateEmployeeException byEmail(String email) {
        return new DuplicateEmployeeException("el correo electrónico", email);
    }
}
