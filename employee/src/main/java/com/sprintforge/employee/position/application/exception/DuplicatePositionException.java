package com.sprintforge.employee.position.application.exception;

import com.sprintforge.common.application.exception.DuplicateEntityException;

public class DuplicatePositionException extends DuplicateEntityException {
    public DuplicatePositionException(String name) {
        super("El puesto con nombre " + name + " ya existe.");
    }
}
