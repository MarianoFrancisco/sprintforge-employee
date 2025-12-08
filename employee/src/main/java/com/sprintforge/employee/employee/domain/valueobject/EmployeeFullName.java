package com.sprintforge.employee.employee.domain.valueobject;

public record EmployeeFullName(String value) {

    public EmployeeFullName(EmployeeFirstName firstName, EmployeeLastName lastName) {
        this(firstName.value() + " " + lastName.value());
    }

    public EmployeeFullName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío");
        }
        if (value.length() > 200) {
            throw new IllegalArgumentException("El nombre completo no puede tener más de 201 caracteres");
        }
    }
}
