package com.sprintforge.employee.employee.domain;

import com.sprintforge.employee.common.domain.exception.ValidationException;
import com.sprintforge.employee.employee.domain.valueobject.*;
import com.sprintforge.employee.position.domain.Position;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Getter
public class Employee {

    private final EmployeeId id;
    private final EmployeeCui cui;
    private final EmployeeEmail email;
    private final Instant createdAt;
    private EmployeeFirstName firstName;
    private EmployeeLastName lastName;
    private EmployeeFullName fullName;
    private EmployeePhoneNumber phoneNumber;
    private EmployeeBirthDate birthDate;
    private EmployeeWorkloadType workloadType;
    private EmployeeSalary salary;
    private EmployeePercentage igssPercentage;
    private EmployeePercentage irtraPercentage;
    private EmployeeProfileImage profileImage;
    private boolean isActive;
    private boolean isDeleted;
    private Instant updatedAt;
    private Position position;

    public Employee(
            String cui,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            LocalDate birthDate,
            Position position,
            EmployeeWorkloadType workloadType,
            BigDecimal salary,
            BigDecimal igssPercentage,
            BigDecimal irtraPercentage,
            String profileImage
    ) {
        Instant timestamp = now();

        this.id = new EmployeeId(randomUUID());
        this.cui = new EmployeeCui(cui);
        this.email = new EmployeeEmail(email);
        this.firstName = new EmployeeFirstName(firstName);
        this.lastName = new EmployeeLastName(lastName);
        this.fullName = new EmployeeFullName(this.firstName, this.lastName);
        this.phoneNumber = new EmployeePhoneNumber(phoneNumber);
        this.birthDate = new EmployeeBirthDate(birthDate);
        this.position = Objects.requireNonNull(position, "El cargo no puede ser nulo");
        this.workloadType = workloadType;
        this.salary = new EmployeeSalary(salary);
        this.igssPercentage = new EmployeePercentage(igssPercentage);
        this.irtraPercentage = new EmployeePercentage(irtraPercentage);
        this.profileImage = new EmployeeProfileImage(profileImage);
        this.isActive = true;
        this.isDeleted = false;
        this.createdAt = timestamp;
        this.updatedAt = timestamp;
    }

    public Employee(
            UUID id,
            String cui,
            String email,
            String firstName,
            String lastName,
            String fullName,
            String phoneNumber,
            LocalDate birthDate,
            Position position,
            String workloadType,
            BigDecimal salary,
            BigDecimal igssPercentage,
            BigDecimal irtraPercentage,
            String profileImage,
            Boolean isActive,
            Boolean isDeleted,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = new EmployeeId(id);
        this.cui = new EmployeeCui(cui);
        this.email = new EmployeeEmail(email);
        this.firstName = new EmployeeFirstName(firstName);
        this.lastName = new EmployeeLastName(lastName);
        this.fullName = new EmployeeFullName(fullName);
        this.phoneNumber = new EmployeePhoneNumber(phoneNumber);
        this.birthDate = new EmployeeBirthDate(birthDate);
        this.position =  Objects.requireNonNull(position, "El cargo no puede ser nulo");
        this.workloadType = EmployeeWorkloadType.safeValueOf(workloadType);
        this.salary = new EmployeeSalary(salary);
        this.igssPercentage = new EmployeePercentage(igssPercentage);
        this.irtraPercentage = new EmployeePercentage(irtraPercentage);
        this.profileImage = new EmployeeProfileImage(profileImage);
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateDetails(
            String firstName,
            String lastName,
            String phoneNumber,
            LocalDate birthDate,
            BigDecimal igssPercentage,
            BigDecimal irtraPercentage,
            String profileImage
    ) {
        this.firstName = new EmployeeFirstName(firstName);
        this.lastName = new EmployeeLastName(lastName);
        this.fullName = new EmployeeFullName(this.firstName, this.lastName);
        this.phoneNumber = new EmployeePhoneNumber(phoneNumber);
        this.birthDate = new EmployeeBirthDate(birthDate);
        this.igssPercentage = new EmployeePercentage(igssPercentage);
        this.irtraPercentage = new EmployeePercentage(irtraPercentage);
        this.profileImage = new EmployeeProfileImage(profileImage);
        this.updatedAt = now();
    }

    public void activate() {
        if (this.isDeleted) {
            throw new ValidationException("No se puede activar un empleado eliminado");
        }
        if (this.isActive) {
            throw new ValidationException("El empleado ya está activo");
        }
        this.isActive = true;
        this.updatedAt = now();
    }

    public void deactivate() {
        if (this.isDeleted) {
            throw new ValidationException("No se puede desactivar un empleado eliminado");
        }
        if (!this.isActive) {
            throw new ValidationException("El empleado ya está inactivo");
        }
        this.isActive = false;
        this.updatedAt = now();
    }

    public void delete() {
        if (this.isDeleted) {
            throw new ValidationException("El empleado ya está eliminado");
        }
        this.isDeleted = true;
        this.updatedAt = now();
    }
}
