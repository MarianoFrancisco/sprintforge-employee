package com.sprintforge.employee.position.domain;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    private static final String NAME = "Developer";
    private static final String DESCRIPTION = "Backend developer";

    @Test
    void shouldCreatePositionAsActiveAndNotDeleted() {
        Position position = new Position(NAME, DESCRIPTION);

        assertAll(
                () -> assertNotNull(position.getId()),
                () -> assertTrue(position.isActive()),
                () -> assertFalse(position.isDeleted()),
                () -> assertNotNull(position.getCreatedAt()),
                () -> assertNotNull(position.getUpdatedAt())
        );
    }

    @Test
    void shouldDeactivateWhenActive() {
        Position position = new Position(NAME, DESCRIPTION);

        position.deactivate();

        assertFalse(position.isActive());
        assertFalse(position.isDeleted());
    }

    @Test
    void shouldActivateWhenInactive() {
        Position position = new Position(NAME, DESCRIPTION);
        position.deactivate();

        position.activate();

        assertTrue(position.isActive());
    }

    @Test
    void shouldDelete() {
        Position position = new Position(NAME, DESCRIPTION);

        position.delete();

        assertTrue(position.isDeleted());
    }

    @Test
    void shouldThrowWhenActivatingDeletedPosition() {
        Position position = new Position(NAME, DESCRIPTION);
        position.delete();

        assertThrows(ValidationException.class, position::activate);
    }

    @Test
    void shouldThrowWhenDeactivatingDeletedPosition() {
        Position position = new Position(NAME, DESCRIPTION);
        position.delete();

        assertThrows(ValidationException.class, position::deactivate);
    }

    @Test
    void shouldThrowWhenDeletingAlreadyDeletedPosition() {
        Position position = new Position(NAME, DESCRIPTION);
        position.delete();

        assertThrows(ValidationException.class, position::delete);
    }

    @Test
    void shouldThrowWhenUpdatingDetailsOfInactivePosition() {
        Position position = new Position(NAME, DESCRIPTION);
        position.deactivate();

        assertThrows(ValidationException.class, () -> position.updateDetails("New Name", "New Desc"));
    }

    @Test
    void shouldThrowWhenUpdatingDetailsOfDeletedPosition() {
        Position position = new Position(NAME, DESCRIPTION);
        position.delete();

        assertThrows(ValidationException.class, () -> position.updateDetails("New Name", "New Desc"));
    }
}
