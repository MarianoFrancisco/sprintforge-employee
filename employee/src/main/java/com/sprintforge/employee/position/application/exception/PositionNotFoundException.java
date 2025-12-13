package com.sprintforge.employee.position.application.exception;

import com.sprintforge.common.application.exception.EntityNotFoundException;

public class PositionNotFoundException extends EntityNotFoundException {
    public PositionNotFoundException(String message) {
        super("Puesto con identificador " + message + " no encontrado.");
    }
}
