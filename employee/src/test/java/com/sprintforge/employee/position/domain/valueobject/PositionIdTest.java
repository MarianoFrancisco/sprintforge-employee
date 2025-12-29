package com.sprintforge.employee.position.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PositionIdTest {

    private static final UUID VALID_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Test
    void shouldCreatePositionIdSuccessfully() {
        PositionId id = new PositionId(VALID_ID);

        assertNotNull(id);
        assertEquals(VALID_ID, id.value());
    }

    @Test
    void shouldThrowValidationExceptionWhenIdIsNull() {
        assertThrows(ValidationException.class, () -> new PositionId(null));
    }
}
