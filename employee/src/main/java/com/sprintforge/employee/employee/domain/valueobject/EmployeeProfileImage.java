package com.sprintforge.employee.employee.domain.valueobject;

public record EmployeeProfileImage(String value) {
    public EmployeeProfileImage {
        if (value != null && value.length() > 300) {
            throw new IllegalArgumentException("La imagen de perfil no puede tener más de 300 caracteres");
        }
    }
}
