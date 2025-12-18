package com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class PaymentSpecs {

    // Filtros por Employee (OR entre campos - nombre o CUI)
    public static Specification<PaymentEntity> employeeFullnameContains(String fullname) {
        return (root, query, criteriaBuilder) -> (fullname == null || fullname.isBlank())
                ? null
                : criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("employee").get("fullname")),
                        "%" + fullname.toLowerCase() + "%");
    }

    public static Specification<PaymentEntity> employeeCuiEquals(String cui) {
        return (root, query, criteriaBuilder) -> (cui == null || cui.isBlank())
                ? null
                : criteriaBuilder.equal(root.join("employee").get("cui"), cui);
    }

    // Filtros por Position
    public static Specification<PaymentEntity> positionNameContains(String positionName) {
        return (root, query, criteriaBuilder) -> (positionName == null || positionName.isBlank())
                ? null
                : criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("employee").join("position").get("name")),
                        "%" + positionName.toLowerCase() + "%");
    }

    public static Specification<PaymentEntity> positionIdEquals(String positionId) {
        return (root, query, criteriaBuilder) -> (positionId == null)
                ? null
                : criteriaBuilder.equal(root.join("employee").join("position").get("id"), positionId);
    }

    // Filtros por fechas de pago
    public static Specification<PaymentEntity> paymentDateGreaterThanOrEqualTo(LocalDate fromDate) {
        return (root, query, criteriaBuilder) -> (fromDate == null)
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("paymentDate"), fromDate);
    }

    public static Specification<PaymentEntity> paymentDateLessThanOrEqualTo(LocalDate toDate) {
        return (root, query, criteriaBuilder) -> (toDate == null)
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("paymentDate"), toDate);
    }

    // Filtro por rango de salario
    public static Specification<PaymentEntity> salaryGreaterThanOrEqualTo(Double minSalary) {
        return (root, query, criteriaBuilder) -> (minSalary == null)
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), minSalary);
    }

    public static Specification<PaymentEntity> salaryLessThanOrEqualTo(Double maxSalary) {
        return (root, query, criteriaBuilder) -> (maxSalary == null)
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("salary"), maxSalary);
    }

    // Filtro por rango de pago total (opcional)
    public static Specification<PaymentEntity> totalGreaterThanOrEqualTo(Double minTotal) {
        return (root, query, criteriaBuilder) -> (minTotal == null)
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("total"), minTotal);
    }

    public static Specification<PaymentEntity> totalLessThanOrEqualTo(Double maxTotal) {
        return (root, query, criteriaBuilder) -> (maxTotal == null)
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("total"), maxTotal);
    }

    public static Specification<PaymentEntity> withFilters(
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