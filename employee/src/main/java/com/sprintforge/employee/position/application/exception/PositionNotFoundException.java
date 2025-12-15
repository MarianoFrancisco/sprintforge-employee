package com.sprintforge.employee.position.application.exception;

import com.sprintforge.common.application.exception.EntityNotFoundException;

public class PositionNotFoundException extends EntityNotFoundException {
    public PositionNotFoundException(String message) {
        super("Cargo con identificador " + message + " no encontrado.");
    }
}
