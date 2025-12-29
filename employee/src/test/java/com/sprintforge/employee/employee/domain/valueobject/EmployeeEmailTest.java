package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeEmailTest {

    private static final String VALID = "john.doe@test.com";
    private static final String INVALID = "john..test.com";
    private static final String BLANK = "   ";

    @Test
    void shouldCreateSuccessfully() {
        EmployeeEmail email = new EmployeeEmail(VALID);
        assertEquals(VALID, email.value());
    }

    @Test
    void shouldThrowWhenNullOrBlank() {
        assertAll(
                () -> assertThrows(ValidationException.class, () -> new EmployeeEmail(null)),
                () -> assertThrows(ValidationException.class, () -> new EmployeeEmail(BLANK))
        );
    }

    @Test
    void shouldThrowWhenTooLong() {
        String longEmail = "a".repeat(95) + "@t.com";
        assertThrows(ValidationException.class, () -> new EmployeeEmail(longEmail));
    }

    @Test
    void shouldThrowWhenInvalidFormat() {
        assertThrows(ValidationException.class, () -> new EmployeeEmail(INVALID));
    }
}
