package com.sprintforge.employee.employee.domain.valueobject;

public enum EmployeeWorkloadType {
    FULL_TIME,
    PART_TIME;
    
    public static EmployeeWorkloadType safeValueOf(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El tipo de jornada no puede estar vacío");
        }
        try {
            return EmployeeWorkloadType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("El tipo de jornada es inválido");
        }
    }
}
