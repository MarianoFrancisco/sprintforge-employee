package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public enum EmploymentHistoryType {
    HIRING,
    REHIRING,
    PROMOTION,
    RAISE,
    SUSPENSION,
    WORKLOAD_CHANGE,
    TERMINATION,
    RESIGNATION;

    public static EmploymentHistoryType safeValueOf(String value) {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El tipo de movimiento no puede estar vacío");
        }
        try {
            return EmploymentHistoryType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("El tipo de movimiento es inválido");
        }
    }
}
