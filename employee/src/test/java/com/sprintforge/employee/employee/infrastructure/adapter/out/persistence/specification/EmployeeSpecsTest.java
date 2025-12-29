package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.employee.employee.domain.valueobject.EmployeeStatus;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeSpecsTest {

    @Test
    void shouldBuildSpecWithNulls() {
        assertDoesNotThrow(() ->
                EmployeeSpecs.withFilters(null, null, null, null)
        );
    }

    @Test
    void shouldBuildSpecWithValues() {
        assertDoesNotThrow(() ->
                EmployeeSpecs.withFilters("john", "dev", EmployeeWorkloadType.FULL_TIME, EmployeeStatus.ACTIVE)
        );
    }
}
