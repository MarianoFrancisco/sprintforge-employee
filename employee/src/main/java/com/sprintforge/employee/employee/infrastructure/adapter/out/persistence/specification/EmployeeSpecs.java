package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.domain.Specification;

@NullMarked
@UtilityClass
public class EmployeeSpecs {

        public Specification<EmployeeEntity> fullnameContains(String fullname) {
                return (root, query, criteriaBuilder) -> (fullname == null || fullname.isBlank())
                                ? null
                                : criteriaBuilder.like(
                                                criteriaBuilder.lower(root.get("fullName")),
                                                "%" + fullname.toLowerCase() + "%");
        }

        public Specification<EmployeeEntity> cuiEquals(String cui) {
                return (root, query, criteriaBuilder) -> (cui == null || cui.isBlank())
                                ? null
                                : criteriaBuilder.equal(root.get("cui"), cui);
        }

        public Specification<EmployeeEntity> phoneNumberEquals(String phoneNumber) {
                return (root, query, criteriaBuilder) -> (phoneNumber == null || phoneNumber.isBlank())
                                ? null
                                : criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber);
        }

        public Specification<EmployeeEntity> emailEquals(String email) {
                return (root, query, criteriaBuilder) -> (email == null || email.isBlank())
                                ? null
                                : criteriaBuilder.equal(root.get("email"), email);
        }

        public Specification<EmployeeEntity> positionNameContains(String positionName) {
                return (root, query, criteriaBuilder) -> (positionName == null || positionName.isBlank())
                                ? null
                                : criteriaBuilder.like(
                                                criteriaBuilder.lower(root.join("position").get("name")),
                                                "%" + positionName.toLowerCase() + "%");
        }

        public Specification<EmployeeEntity> positionIdEquals(String positionId) {
                return (root, query, criteriaBuilder) -> (positionId == null)
                                ? null
                                : criteriaBuilder.equal(root.join("position").get("id"), positionId);
        }

        public Specification<EmployeeEntity> statusEquals(EmployeeStatus status) {
                return (root, query, criteriaBuilder) -> (status == null)
                                ? null
                                : criteriaBuilder.equal(root.get("status"), status);
        }

        public Specification<EmployeeEntity> workloadTypeEquals(EmployeeWorkloadType workloadType) {
                return (root, query, criteriaBuilder) -> (workloadType == null)
                                ? null
                                : criteriaBuilder.equal(root.get("workloadType"), workloadType);
        }

        public Specification<EmployeeEntity> withFilters(
                        String employeeSearch,
                        String positionSearch,
                        EmployeeWorkloadType workloadType,
                        EmployeeStatus status) {
                return Specification.allOf(
                                Specification.anyOf(
                                                fullnameContains(employeeSearch),
                                                cuiEquals(employeeSearch),
                                                phoneNumberEquals(employeeSearch),
                                                emailEquals(employeeSearch)),
                                positionNameContains(positionSearch),
                                workloadTypeEquals(workloadType),
                                statusEquals(status));
        }
}
