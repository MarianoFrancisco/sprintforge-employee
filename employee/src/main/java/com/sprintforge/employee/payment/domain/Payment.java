package com.sprintforge.employee.payment.domain;

import java.util.UUID;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.payment.domain.valueobject.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    private final PaymentId id;
    @NonNull
    private final Employee employee;
    private final PaymentDate date;
    private final Money baseSalary;
    private final Money bonus;
    private final Money deduction;
    private final Money total;
    private final PaymentNotes notes;

    public static Payment create(
            Employee employee,
            PaymentDate date,
            Money bonus,
            Money deduction,
            PaymentNotes notes) {
        if (employee.getStatus() != EmployeeStatus.ACTIVE) {
            throw new ValidationException("No se puede procesar el pago de un empleado que no está activo");
        }
        
        Money baseSalary = employee.getSalary();
        if (deduction.isGreaterThanOrEqual(baseSalary.plus(bonus))) {
            throw new ValidationException("El total de descuento no puede ser mayor o igual al salario base más bonos");
        }

        Money total = baseSalary.plus(bonus).minus(deduction);
        return new Payment(
                new PaymentId(UUID.randomUUID()),
                employee,
                date,
                baseSalary,
                bonus,
                deduction,
                total,
                notes);
    }

    public static Payment rehydrate(
            UUID id,
            Employee employee,
            PaymentDate date,
            Money baseSalary,
            Money bonus,
            Money deduction,
            Money total,
            PaymentNotes notes) {
        return new Payment(
                new PaymentId(id),
                employee,
                date,
                baseSalary,
                bonus,
                deduction,
                total,
                notes);
    }

}
