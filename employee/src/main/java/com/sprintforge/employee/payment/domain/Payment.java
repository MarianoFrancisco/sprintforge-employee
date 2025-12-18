package com.sprintforge.employee.payment.domain;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.employee.common.domain.valueobject.Money;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.payment.domain.valueobject.PaymentDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    @NonNull
    private final Employee employee;
    private final PaymentDate date;
    private final Money baseSalary;
    private final Money bonus;
    private final Money deduction;
    private final Money total;

    public static Payment create(
            Employee employee,
            PaymentDate date,
            Money baseSalary,
            Money bonus,
            Money deduction) {
        if (deduction.isGreaterThanOrEqual(baseSalary.plus(bonus))) {
            throw new ValidationException("El total de descuento no puede ser mayor o igual al salario base más bonos");
        }

        Money total = baseSalary.plus(bonus).minus(deduction);
        return new Payment(
                employee,
                date,
                baseSalary,
                bonus,
                deduction,
                total);
    }

    public static Payment rehydrate(
            Employee employee,
            PaymentDate date,
            Money baseSalary,
            Money bonus,
            Money deduction,
            Money total) {
        return new Payment(
                employee,
                date,
                baseSalary,
                bonus,
                deduction,
                total);
    }

}
