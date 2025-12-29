package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeWorkloadTypeTest {

    @Test
    void shouldParseSafely() {
        assertAll(
                () -> assertEquals(EmployeeWorkloadType.FULL_TIME, EmployeeWorkloadType.safeValueOf("FULL_TIME")),
                () -> assertEquals(EmployeeWorkloadType.FULL_TIME, EmployeeWorkloadType.safeValueOf(" full_time ")),
                () -> assertEquals(EmployeeWorkloadType.PART_TIME, EmployeeWorkloadType.safeValueOf("PART_TIME"))
        );
    }

    @Test
    void shouldThrowWhenNullOrBlank() {
        assertAll(
                () -> assertThrows(ValidationException.class, () -> EmployeeWorkloadType.safeValueOf(null)),
                () -> assertThrows(ValidationException.class, () -> EmployeeWorkloadType.safeValueOf("   "))
        );
    }

    @Test
    void shouldThrowWhenInvalid() {
        assertThrows(ValidationException.class, () -> EmployeeWorkloadType.safeValueOf("INVALID"));
    }
}
