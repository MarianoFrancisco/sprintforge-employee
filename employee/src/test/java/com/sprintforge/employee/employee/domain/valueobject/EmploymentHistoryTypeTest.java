package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmploymentHistoryTypeTest {

    @Test
    void shouldParseSafely() {
        assertAll(
                () -> assertEquals(EmploymentHistoryType.HIRING, EmploymentHistoryType.safeValueOf("HIRING")),
                () -> assertEquals(EmploymentHistoryType.SALARY_INCREASE, EmploymentHistoryType.safeValueOf(" salary_increase ")),
                () -> assertEquals(EmploymentHistoryType.TERMINATION, EmploymentHistoryType.safeValueOf("termination"))
        );
    }

    @Test
    void shouldThrowWhenNullOrBlank() {
        assertAll(
                () -> assertThrows(ValidationException.class, () -> EmploymentHistoryType.safeValueOf(null)),
                () -> assertThrows(ValidationException.class, () -> EmploymentHistoryType.safeValueOf("   "))
        );
    }

    @Test
    void shouldThrowWhenInvalid() {
        assertThrows(ValidationException.class, () -> EmploymentHistoryType.safeValueOf("NOPE"));
    }
}
