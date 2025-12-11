package com.sprintforge.employee.employee.application.exception;

import com.sprintforge.employee.common.application.exception.EntityNotFoundException;

import java.util.UUID;

public class EmployeeNotFoundException extends EntityNotFoundException {

    private EmployeeNotFoundException(String field, String value) {
        super(String.format("No se encontró ningún empleado con %s \"%s\".", field, value));
    }

    public static EmployeeNotFoundException byId(UUID id) {
        return new EmployeeNotFoundException("el identificador", id.toString());
    }

    public static EmployeeNotFoundException byCui(String cui) {
        return new EmployeeNotFoundException("el CUI", cui);
    }

    public static EmployeeNotFoundException byEmail(String email) {
        return new EmployeeNotFoundException("el correo electrónico", email);
    }

    public static EmployeeNotFoundException byPhone(String phoneNumber) {
        return new EmployeeNotFoundException("el número de teléfono", phoneNumber);
    }
}
