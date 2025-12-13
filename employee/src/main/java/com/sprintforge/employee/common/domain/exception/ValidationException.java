package com.sprintforge.employee.common.domain.exception;

public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException() {
        super("Ha ocurrido un error de validación.");
    }
}
