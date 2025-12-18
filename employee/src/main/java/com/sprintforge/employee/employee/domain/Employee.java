package com.sprintforge.employee.employee.domain;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.domain.valueobject.*;
import com.sprintforge.employee.position.domain.Position;

import lombok.*;

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
    private Money salary;
    private EmployeeProfileImage profileImage;
    private EmployeeStatus status;

    public static Employee hire(
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
                new Money(salary),
                new EmployeeProfileImage(profileImage),
                EmployeeStatus.ACTIVE);
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
            EmployeeStatus status) {
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
                new Money(salary),
                new EmployeeProfileImage(profileImage),
                status);
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

    public void increaseSalary(BigDecimal amount) {
        if (this.status == EmployeeStatus.TERMINATED) {
            throw new ValidationException("No se puede aumentar salario a un empleado despedido");
        }

        if (this.status == EmployeeStatus.SUSPENDED) {
            throw new ValidationException("No se puede aumentar salario a un empleado suspendido");
        }

        this.salary = this.salary.plus(new Money(amount));
    }

    public void suspend() {
        if (this.status == EmployeeStatus.TERMINATED) {
            throw new ValidationException("Un empleado despedido no puede ser suspendido");
        }

        if (this.status == EmployeeStatus.SUSPENDED) {
            throw new ValidationException("El empleado ya está suspendido");
        }

        this.status = EmployeeStatus.SUSPENDED;
    }

    public void reinstate() {
        if (this.status != EmployeeStatus.SUSPENDED) {
            throw new ValidationException("Solo se pueden reinstalar empleados suspendidos");
            
        }

        this.status = EmployeeStatus.ACTIVE;
    }

    public void terminate() {
        if (this.status == EmployeeStatus.TERMINATED) {
            throw new ValidationException("El empleado ya está despedido");
        }

        this.status = EmployeeStatus.TERMINATED;
    }
}
