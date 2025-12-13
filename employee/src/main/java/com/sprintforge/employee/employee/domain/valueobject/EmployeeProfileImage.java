package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.employee.common.domain.exception.ValidationException;

public record EmployeeProfileImage(String value) {
    public EmployeeProfileImage {
        if (value != null && value.length() > 300) {
            throw new ValidationException("La imagen de perfil no puede tener más de 300 caracteres");
        }
    }
}
