package com.sprintforge.employee.employee.domain.valueobject;

public record EmploymentHistoryEmployee(
        String cui,
        String email,
        String firstName,
        String lastName,
        String fullName,
        String phoneNumber,
        String position,
        String profileImage
) {
}
