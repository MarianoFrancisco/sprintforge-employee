package com.sprintforge.employee.employee.domain;

import com.sprintforge.employee.employee.domain.valueobject.*;
import com.sprintforge.employee.position.domain.Position;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "cui")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Employee {

    private final EmployeeId id;
    private final EmployeeCui cui;
    private final EmployeeEmail email;
    private EmployeeFirstName firstName;
    private EmployeeLastName lastName;
    private String fullName;
    private EmployeePhoneNumber phoneNumber;
    private EmployeeBirthDate birthDate;
    @NonNull
    private Position position;
    private EmployeeWorkloadType workloadType;
    private EmployeeSalary salary;
    private EmployeeProfileImage profileImage;
    private boolean isActive;

    public static Employee create(
            String cui,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            LocalDate birthDate,
            Position position,
            EmployeeWorkloadType workloadType,
            BigDecimal salary,
            String profileImage) {
        return new Employee(
                new EmployeeId(UUID.randomUUID()),
                new EmployeeCui(cui),
                new EmployeeEmail(email),
                new EmployeeFirstName(firstName),
                new EmployeeLastName(lastName),
                firstName + " " + lastName,
                new EmployeePhoneNumber(phoneNumber),
                new EmployeeBirthDate(birthDate),
                position,
                Objects.requireNonNullElse(workloadType, EmployeeWorkloadType.FULL_TIME),
                new EmployeeSalary(salary),
                new EmployeeProfileImage(profileImage),
                true);
    }

    public static Employee rehydrate(
            UUID id,
            String cui,
            String email,
            String firstName,
            String lastName,
            String fullName,
            String phoneNumber,
            LocalDate birthDate,
            Position position,
            EmployeeWorkloadType workloadType,
            BigDecimal salary,
            String profileImage,
            boolean isActive) {
        return new Employee(
                new EmployeeId(id),
                new EmployeeCui(cui),
                new EmployeeEmail(email),
                new EmployeeFirstName(firstName),
                new EmployeeLastName(lastName),
                fullName,
                new EmployeePhoneNumber(phoneNumber),
                new EmployeeBirthDate(birthDate),
                position,
                workloadType,
                new EmployeeSalary(salary),
                new EmployeeProfileImage(profileImage),
                isActive);
    }

    public void updateDetails(
            String firstName,
            String lastName,
            String phoneNumber,
            LocalDate birthDate,
            String profileImage) {
        this.firstName = new EmployeeFirstName(firstName);
        this.lastName = new EmployeeLastName(lastName);
        this.fullName = firstName + " " + lastName;
        this.phoneNumber = new EmployeePhoneNumber(phoneNumber);
        this.birthDate = new EmployeeBirthDate(birthDate);
        this.profileImage = new EmployeeProfileImage(profileImage);
    }
}
