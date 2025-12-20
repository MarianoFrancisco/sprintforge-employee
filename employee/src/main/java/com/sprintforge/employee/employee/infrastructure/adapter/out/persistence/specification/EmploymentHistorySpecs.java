package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmploymentHistoryEntity;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmploymentHistorySpecs {

    // Filtros por Employee (OR entre campos)
    public Specification<EmploymentHistoryEntity> employeeFullnameContains(String fullname) {
        return (root, query, criteriaBuilder) -> (fullname == null || fullname.isBlank())
                ? null
                : criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("employee").get("fullname")),
                        "%" + fullname.toLowerCase() + "%");
    }

    public Specification<EmploymentHistoryEntity> employeeCuiEquals(String cui) {
        return (root, query, criteriaBuilder) -> (cui == null || cui.isBlank())
                ? null
                : criteriaBuilder.equal(root.join("employee").get("cui"), cui);
    }

    public Specification<EmploymentHistoryEntity> positionNameContains(String positionName) {
        return (root, query, criteriaBuilder) -> (positionName == null || positionName.isBlank())
                ? null
                : criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("employee").join("position").get("name")),
                        "%" + positionName.toLowerCase() + "%");
    }

    public Specification<EmploymentHistoryEntity> positionIdEquals(String positionId) {
        return (root, query, criteriaBuilder) -> (positionId == null)
                ? null
                : criteriaBuilder.equal(root.join("employee").join("position").get("id"), positionId);
    }

    // Filtros por fechas
    public Specification<EmploymentHistoryEntity> startDateGreaterThanOrEqualTo(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> (startDate == null)
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    public Specification<EmploymentHistoryEntity> startDateLessThanOrEqualTo(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> (endDate == null)
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), endDate);
    }

    public Specification<EmploymentHistoryEntity> endDateGreaterThanOrEqualTo(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> (startDate == null)
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), startDate);
    }

    public Specification<EmploymentHistoryEntity> endDateLessThanOrEqualTo(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> (endDate == null)
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate);
    }

    // Filtro por tipo de período
    public Specification<EmploymentHistoryEntity> periodTypeEquals(EmploymentHistoryType periodType) {
        return (root, query, criteriaBuilder) -> (periodType == null)
                ? null
                : criteriaBuilder.equal(root.get("type"), periodType);
    }

    public Specification<EmploymentHistoryEntity> withFilters(
            String employee,
            String position,
            EmploymentHistoryType type,
            LocalDate startDateFrom,
            LocalDate startDateTo) {

        Specification<EmploymentHistoryEntity> spec = Specification.allOf(
            Specification.anyOf(
                employeeFullnameContains(employee),
                employeeCuiEquals(employee)
            ),
            Specification.anyOf(
                positionNameContains(position),
                positionIdEquals(position)
            ),
            periodTypeEquals(type),
            startDateGreaterThanOrEqualTo(startDateFrom),
            startDateLessThanOrEqualTo(startDateTo)
        );
        return spec;
    }
}
