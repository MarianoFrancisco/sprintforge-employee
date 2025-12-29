package com.sprintforge.employee.position.application.service;

import com.sprintforge.employee.position.application.exception.DuplicatePositionException;
import com.sprintforge.employee.position.application.port.in.command.CreatePositionCommand;
import com.sprintforge.employee.position.application.port.out.persistence.ExistsPositionByName;
import com.sprintforge.employee.position.application.port.out.persistence.SavePosition;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePositionImplTest {

    private static final String NAME = "Developer";
    private static final String DESCRIPTION = "Backend developer";

    @Mock
    private ExistsPositionByName existsPositionByName;

    @Mock
    private SavePosition savePosition;

    @InjectMocks
    private CreatePositionImpl serviceToTest;

    @Test
    void shouldCreatePositionSuccessfully() {
        CreatePositionCommand command = new CreatePositionCommand(NAME, DESCRIPTION);

        when(existsPositionByName.existsByName(NAME)).thenReturn(false);

        ArgumentCaptor<Position> captor = ArgumentCaptor.forClass(Position.class);
        when(savePosition.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        Position result = serviceToTest.handle(command);

        assertAll(
                () -> assertNotNull(result),
                () -> assertNotNull(result.getId()),
                () -> assertEquals(NAME, result.getName().value()),
                () -> assertEquals(DESCRIPTION, result.getDescription().value()),
                () -> assertTrue(result.isActive()),
                () -> assertFalse(result.isDeleted())
        );

        Position persisted = captor.getValue();
        assertAll(
                () -> assertNotNull(persisted),
                () -> assertEquals(NAME, persisted.getName().value()),
                () -> assertEquals(DESCRIPTION, persisted.getDescription().value())
        );

        verify(existsPositionByName, times(1)).existsByName(NAME);
        verify(savePosition, times(1)).save(any(Position.class));
        verifyNoMoreInteractions(existsPositionByName, savePosition);
    }

    @Test
    void shouldThrowDuplicatePositionExceptionWhenNameAlreadyExists() {
        CreatePositionCommand command = new CreatePositionCommand(NAME, DESCRIPTION);

        when(existsPositionByName.existsByName(NAME)).thenReturn(true);

        assertThrows(DuplicatePositionException.class, () -> serviceToTest.handle(command));

        verify(existsPositionByName, times(1)).existsByName(NAME);
        verify(savePosition, never()).save(any(Position.class));
        verifyNoMoreInteractions(existsPositionByName, savePosition);
    }
}
