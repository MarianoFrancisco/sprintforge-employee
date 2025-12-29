package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.employee.employee.domain.valueobject.EmploymentHistoryType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmploymentHistorySpecsTest {

    @Test
    void shouldBuildSpecWithNulls() {
        assertDoesNotThrow(() ->
                EmploymentHistorySpecs.withFilters(null, null, null, null, null)
        );
    }

    @Test
    void shouldBuildSpecWithValues() {
        assertDoesNotThrow(() ->
                EmploymentHistorySpecs.withFilters(
                        "john",
                        "dev",
                        EmploymentHistoryType.HIRING,
                        LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 1, 31)
                )
        );
    }
}
