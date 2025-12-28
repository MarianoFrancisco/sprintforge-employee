package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public enum EmployeeWorkloadType {
    FULL_TIME,
    PART_TIME;
    
    public static EmployeeWorkloadType safeValueOf(String value) {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El tipo de jornada no puede estar vacío");
        }
        try {
            return EmployeeWorkloadType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("El tipo de jornada es inválido");
        }
    }
}
