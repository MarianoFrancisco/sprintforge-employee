package com.sprintforge.employee.employee.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

import java.util.Set;
import java.util.UUID;

public class InvalidEmployeesException extends BusinessException {
    public InvalidEmployeesException(Set<UUID> invalidIds) {
        super("El listado proporcionado contiene empleados inexistentes: " + invalidIds);
    }
}
