package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@NullMarked
@UtilityClass
public class EmployeeSpecs {

    public Specification<EmployeeEntity> searchLike(String searchTerm) {
        String pattern = "%" + searchTerm.toLowerCase() + "%";

        return (root, ignored, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get("cui")), pattern),
                        cb.like(cb.lower(root.get("email")), pattern),
                        cb.like(cb.lower(root.get("phoneNumber")), pattern),
                        cb.like(
                                cb.lower(
                                        cb.concat(
                                                cb.concat(root.get("firstName"), " "),
                                                root.get("lastName")
                                        )
                                ),
                                pattern
                        )
                );
    }

    public Specification<EmployeeEntity> isActive(Boolean isActive) {
        return (root, ignored, cb) ->
                cb.equal(root.get("isActive"), isActive);
    }

    public Specification<EmployeeEntity> isDeleted(Boolean isDeleted) {
        return (root, ignored, cb) ->
                cb.equal(root.get("isDeleted"), isDeleted);
    }

    public Specification<EmployeeEntity> withFilters(
            @Nullable String searchTerm,
            @Nullable Boolean isActive,
            @Nullable Boolean isDeleted
    ) {
        Specification<EmployeeEntity> spec =
                (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();

        if (isNotBlank(searchTerm)) {
            spec = spec.and(searchLike(searchTerm));
        }
        if (isActive != null) {
            spec = spec.and(isActive(isActive));
        }
        if (isDeleted != null) {
            spec = spec.and(isDeleted(isDeleted));
        }

        return spec;
    }
}
