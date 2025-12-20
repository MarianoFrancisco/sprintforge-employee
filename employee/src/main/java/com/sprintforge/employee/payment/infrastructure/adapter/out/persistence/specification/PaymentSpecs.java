package com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;

import lombok.experimental.UtilityClass;

import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

@UtilityClass
public class PaymentSpecs {

    // Filtros por Employee (OR entre campos - nombre o CUI)
    public Specification<PaymentEntity> employeeFullnameContains(String fullname) {
        return (root, query, criteriaBuilder) -> (fullname == null || fullname.isBlank())
                ? null
                : criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("employee").get("fullname")),
                        "%" + fullname.toLowerCase() + "%");
    }

    public Specification<PaymentEntity> employeeCuiEquals(String cui) {
        return (root, query, criteriaBuilder) -> (cui == null || cui.isBlank())
                ? null
                : criteriaBuilder.equal(root.join("employee").get("cui"), cui);
    }

    // Filtros por Position
    public Specification<PaymentEntity> positionNameContains(String positionName) {
        return (root, query, criteriaBuilder) -> (positionName == null || positionName.isBlank())
                ? null
                : criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("employee").join("position").get("name")),
                        "%" + positionName.toLowerCase() + "%");
    }

    public Specification<PaymentEntity> positionIdEquals(String positionId) {
        return (root, query, criteriaBuilder) -> (positionId == null)
                ? null
                : criteriaBuilder.equal(root.join("employee").join("position").get("id"), positionId);
    }

    // Filtros por fechas de pago
    public Specification<PaymentEntity> paymentDateGreaterThanOrEqualTo(LocalDate fromDate) {
        return (root, query, criteriaBuilder) -> (fromDate == null)
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("date"), fromDate);
    }

    public Specification<PaymentEntity> paymentDateLessThanOrEqualTo(LocalDate toDate) {
        return (root, query, criteriaBuilder) -> (toDate == null)
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("date"), toDate);
    }

    // Filtro por rango de salario
    public Specification<PaymentEntity> salaryGreaterThanOrEqualTo(Double minSalary) {
        return (root, query, criteriaBuilder) -> (minSalary == null)
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), minSalary);
    }

    public Specification<PaymentEntity> salaryLessThanOrEqualTo(Double maxSalary) {
        return (root, query, criteriaBuilder) -> (maxSalary == null)
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("salary"), maxSalary);
    }

    // Filtro por rango de pago total (opcional)
    public Specification<PaymentEntity> totalGreaterThanOrEqualTo(Double minTotal) {
        return (root, query, criteriaBuilder) -> (minTotal == null)
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("total"), minTotal);
    }

    public Specification<PaymentEntity> totalLessThanOrEqualTo(Double maxTotal) {
        return (root, query, criteriaBuilder) -> (maxTotal == null)
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("total"), maxTotal);
    }

    public Specification<PaymentEntity> withFilters(
            String employeeSearch,
            String positionSearch,
            LocalDate paymentDateFrom,
            LocalDate paymentDateTo) {

        return Specification.allOf(
                Specification.anyOf(
                        employeeFullnameContains(employeeSearch),
                        employeeCuiEquals(employeeSearch)),
                Specification.anyOf(
                        positionNameContains(positionSearch),
                        positionIdEquals(null)),
                paymentDateGreaterThanOrEqualTo(paymentDateFrom),
                paymentDateLessThanOrEqualTo(paymentDateTo));
    }
}