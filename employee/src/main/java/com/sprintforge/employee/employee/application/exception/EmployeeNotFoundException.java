package com.sprintforge.employee.employee.application.exception;

import java.util.UUID;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String type, String value) {
        super("Empleado con " + type + " " + value + " no encontrado.");
    }
}
