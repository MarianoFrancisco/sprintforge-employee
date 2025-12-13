package com.sprintforge.employee.common.application.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException() {
        super("La entidad solicitada no fue encontrada.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}