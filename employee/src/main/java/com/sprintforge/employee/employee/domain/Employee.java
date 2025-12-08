package com.sprintforge.employee.employee.domain;

import com.sprintforge.employee.employee.domain.valueobject.EmployeeId;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeCui;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeEmail;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeFirstName;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeLastName;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeFullName;
import com.sprintforge.employee.employee.domain.valueobject.EmployeePhoneNumber;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeBirthDate;
import com.sprintforge.employee.employee.domain.valueobject.EmployeePositionId;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeSalary;
import com.sprintforge.employee.employee.domain.valueobject.EmployeePercentage;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeProfileImage;
import com.sprintforge.employee.employee.domain.valueobject.EmployeePosition;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;
import static java.util.UUID.randomUUID;

@Getter
public class Employee {

    private final EmployeeId id;
    private final EmployeeCui cui;
    private final EmployeeEmail email;
    private EmployeeFirstName firstName;
    private EmployeeLastName lastName;
    private EmployeeFullName fullName;
    private EmployeePhoneNumber phoneNumber;
    private EmployeeBirthDate birthDate;
    private EmployeePositionId positionId;
    private EmployeeWorkloadType workloadType;
    private EmployeeSalary salary;
    private EmployeePercentage igssPercentage;
    private EmployeePercentage irtraPercentage;
    private EmployeeProfileImage profileImage;
    private Boolean isActive;
    private Boolean isDeleted;
    private final Long createdAt;
    private Long updatedAt;
    @Setter
    private EmployeePosition position;

    public Employee(
            String cui,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            LocalDate birthDate,
            UUID positionId,
            EmployeeWorkloadType workloadType,
            BigDecimal salary,
            BigDecimal igssPercentage,
            BigDecimal irtraPercentage,
            String profileImage
    ) {
        long now = currentTimeMillis();

        this.id = new EmployeeId(randomUUID());
        this.cui = new EmployeeCui(cui);
        this.email = new EmployeeEmail(email);
        this.firstName = new EmployeeFirstName(firstName);
        this.lastName = new EmployeeLastName(lastName);
        this.fullName = new EmployeeFullName(this.firstName, this.lastName);
        this.phoneNumber = new EmployeePhoneNumber(phoneNumber);
        this.birthDate = new EmployeeBirthDate(birthDate);
        this.positionId = new EmployeePositionId(positionId);
        this.workloadType = workloadType;
        this.salary = new EmployeeSalary(salary);
        this.igssPercentage = new EmployeePercentage(igssPercentage);
        this.irtraPercentage = new EmployeePercentage(irtraPercentage);
        this.profileImage = new EmployeeProfileImage(profileImage);
        this.isActive = true;
        this.isDeleted = false;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Employee(
            UUID id,
            String cui,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            LocalDate birthDate,
            UUID positionId,
            String workloadType,
            BigDecimal salary,
            BigDecimal igssPercentage,
            BigDecimal irtraPercentage,
            String profileImage,
            Boolean isActive,
            Boolean isDeleted,
            Long createdAt,
            Long updatedAt
    ) {
        this.id = new EmployeeId(id);
        this.cui = new EmployeeCui(cui);
        this.email = new EmployeeEmail(email);
        this.firstName = new EmployeeFirstName(firstName);
        this.lastName = new EmployeeLastName(lastName);
        this.fullName = new EmployeeFullName(this.firstName, this.lastName);
        this.phoneNumber = new EmployeePhoneNumber(phoneNumber);
        this.birthDate = new EmployeeBirthDate(birthDate);
        this.positionId = new EmployeePositionId(positionId);
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
            UUID positionId,
            EmployeeWorkloadType workloadType,
            BigDecimal igssPercentage,
            BigDecimal irtraPercentage,
            String profileImage
    ) {
        this.firstName = new EmployeeFirstName(firstName);
        this.lastName = new EmployeeLastName(lastName);
        this.fullName = new EmployeeFullName(this.firstName, this.lastName);
        this.phoneNumber = new EmployeePhoneNumber(phoneNumber);
        this.birthDate = new EmployeeBirthDate(birthDate);
        this.positionId = new EmployeePositionId(positionId);
        this.workloadType = workloadType;
        this.igssPercentage = new EmployeePercentage(igssPercentage);
        this.irtraPercentage = new EmployeePercentage(irtraPercentage);
        this.profileImage = new EmployeeProfileImage(profileImage);
        this.updatedAt = currentTimeMillis();
    }

    public void activate() {
        if (this.isDeleted) {
            throw new IllegalStateException("No se puede activar un empleado eliminado");
        }
        this.isActive = true;
        this.updatedAt = currentTimeMillis();
    }

    public void deactivate() {
        if (this.isDeleted) {
            throw new IllegalStateException("No se puede desactivar un empleado eliminado");
        }
        this.isActive = false;
        this.updatedAt = currentTimeMillis();
    }

    public void delete() {
        this.isDeleted = true;
        this.updatedAt = currentTimeMillis();
    }
}
