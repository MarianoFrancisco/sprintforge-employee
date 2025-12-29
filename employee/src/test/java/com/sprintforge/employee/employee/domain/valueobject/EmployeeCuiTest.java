package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeCuiTest {

    private static final String VALID_CUI = "1234567890101";
    private static final String INVALID_SHORT = "123";
    private static final String INVALID_LETTERS = "1234567890ABC";
    private static final String BLANK = "   ";

    @Test
    void shouldCreateSuccessfully() {
        EmployeeCui cui = new EmployeeCui(VALID_CUI);
        assertEquals(VALID_CUI, cui.value());
    }

    @Test
    void shouldThrowWhenNullOrBlank() {
        assertAll(
                () -> assertThrows(ValidationException.class, () -> new EmployeeCui(null)),
                () -> assertThrows(ValidationException.class, () -> new EmployeeCui(BLANK))
        );
    }

    @Test
    void shouldThrowWhenNot13Digits() {
        assertAll(
                () -> assertThrows(ValidationException.class, () -> new EmployeeCui(INVALID_SHORT)),
                () -> assertThrows(ValidationException.class, () -> new EmployeeCui(INVALID_LETTERS))
        );
    }
}
