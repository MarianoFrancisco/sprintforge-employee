package com.sprintforge.employee.position.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionDescriptionTest {

    private static final String VALID = "Backend developer";
    private static final String VALID_WITH_SPACES = "   Backend developer   ";
    private static final String VALID_TRIMMED = "Backend developer";

    @Test
    void shouldCreatePositionDescriptionSuccessfully() {
        PositionDescription desc = new PositionDescription(VALID);

        assertNotNull(desc);
        assertEquals(VALID, desc.value());
    }

    @Test
    void shouldAllowNull() {
        PositionDescription desc = new PositionDescription(null);

        assertNull(desc.value());
    }

    @Test
    void shouldTrimValueWhenNotNull() {
        PositionDescription desc = new PositionDescription(VALID_WITH_SPACES);

        assertEquals(VALID_TRIMMED, desc.value());
    }

    @Test
    void shouldThrowValidationExceptionWhenLengthGreaterThan255() {
        String tooLong = "a".repeat(256);

        assertThrows(ValidationException.class, () -> new PositionDescription(tooLong));
    }
}
