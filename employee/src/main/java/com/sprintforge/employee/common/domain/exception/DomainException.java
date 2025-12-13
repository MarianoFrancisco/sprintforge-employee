package com.sprintforge.employee.common.domain.exception;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }

    public DomainException() {
        super("Ha ocurrido un error en el dominio.");
    }
    
}
