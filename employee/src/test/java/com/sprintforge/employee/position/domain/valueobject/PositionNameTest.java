package com.sprintforge.employee.position.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionNameTest {

    private static final String VALID = "Developer";
    private static final String VALID_WITH_SPACES = "   Developer   ";
    private static final String VALID_TRIMMED = "Developer";

    private static final String EMPTY = "";
    private static final String BLANK = "   ";

    @Test
    void shouldCreatePositionNameSuccessfully() {
        PositionName name = new PositionName(VALID);

        assertNotNull(name);
        assertEquals(VALID, name.value());
    }

    @Test
    void shouldTrimValue() {
        PositionName name = new PositionName(VALID_WITH_SPACES);

        assertEquals(VALID_TRIMMED, name.value());
    }

    @Test
    void shouldThrowValidationExceptionWhenNullOrBlank() {
        assertAll(
                () -> assertThrows(ValidationException.class, () -> new PositionName(null)),
                () -> assertThrows(ValidationException.class, () -> new PositionName(EMPTY)),
                () -> assertThrows(ValidationException.class, () -> new PositionName(BLANK))
        );
    }

    @Test
    void shouldThrowValidationExceptionWhenLengthGreaterThan100() {
        String tooLong = "a".repeat(101);

        assertThrows(ValidationException.class, () -> new PositionName(tooLong));
    }
}
