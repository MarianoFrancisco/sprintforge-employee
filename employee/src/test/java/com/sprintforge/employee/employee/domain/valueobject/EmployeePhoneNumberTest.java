package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeePhoneNumberTest {

    private static final String VALID = "55556666";
    private static final String INVALID_SHORT = "123";
    private static final String INVALID_LETTERS = "5555ABCD";

    @Test
    void shouldCreateSuccessfully() {
        EmployeePhoneNumber phone = new EmployeePhoneNumber(VALID);
        assertEquals(VALID, phone.value());
    }

    @Test
    void shouldThrowWhenNullOrBlank() {
        assertAll(
                () -> assertThrows(ValidationException.class, () -> new EmployeePhoneNumber(null)),
                () -> assertThrows(ValidationException.class, () -> new EmployeePhoneNumber("   "))
        );
    }

    @Test
    void shouldThrowWhenNot8Digits() {
        assertAll(
                () -> assertThrows(ValidationException.class, () -> new EmployeePhoneNumber(INVALID_SHORT)),
                () -> assertThrows(ValidationException.class, () -> new EmployeePhoneNumber(INVALID_LETTERS))
        );
    }
}
