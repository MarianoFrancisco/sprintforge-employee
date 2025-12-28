package com.sprintforge.employee.employee.domain.valueobject;

import java.util.UUID;

import com.sprintforge.common.domain.exception.ValidationException;

public record EmployeeProfileImageKey(String value) {
    public EmployeeProfileImageKey {
        if (value != null && value.length() > 300) {
            throw new ValidationException("La key de la imagen de perfil no puede tener más de 300 caracteres");
        }
    }

    public static EmployeeProfileImageKey generate() {
        return new EmployeeProfileImageKey(UUID.randomUUID().toString());
    }

    public static EmployeeProfileImageKey empty() {
        return new EmployeeProfileImageKey(null);
    }

    public boolean isEmpty() {
        return this.value == null;
    }
}
