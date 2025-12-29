package com.sprintforge.employee.position.application.service;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.employee.position.application.exception.PositionNotFoundException;
import com.sprintforge.employee.position.application.port.in.command.DeactivatePositionCommand;
import com.sprintforge.employee.position.application.port.out.persistence.FindPositionById;
import com.sprintforge.employee.position.application.port.out.persistence.SavePosition;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeactivatePositionImplTest {

    private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final String NAME = "Developer";
    private static final String DESCRIPTION = "Backend developer";
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    @Mock
    private FindPositionById findById;

    @Mock
    private SavePosition savePosition;

    @InjectMocks
    private DeactivatePositionImpl serviceToTest;

    private Position activePosition;

    @BeforeEach
    void setUp() {
        activePosition = new Position(ID, NAME, DESCRIPTION, true, false, NOW, NOW);
    }

    @Test
    void shouldDeactivatePositionSuccessfully() {
        when(findById.findById(ID)).thenReturn(Optional.of(activePosition));
        when(savePosition.save(any(Position.class))).thenAnswer(inv -> inv.getArgument(0));

        Position result = serviceToTest.handle(new DeactivatePositionCommand(ID));

        assertFalse(result.isActive());
        verify(findById).findById(ID);
        verify(savePosition).save(activePosition);
        verifyNoMoreInteractions(findById, savePosition);
    }

    @Test
    void shouldThrowPositionNotFoundExceptionWhenNotExists() {
        when(findById.findById(ID)).thenReturn(Optional.empty());

        assertThrows(PositionNotFoundException.class, () -> serviceToTest.handle(new DeactivatePositionCommand(ID)));

        verify(findById).findById(ID);
        verify(savePosition, never()).save(any());
        verifyNoMoreInteractions(findById, savePosition);
    }

    @Test
    void shouldPropagateValidationExceptionWhenAlreadyInactive() {
        Position alreadyInactive = new Position(ID, NAME, DESCRIPTION, false, false, NOW, NOW);
        when(findById.findById(ID)).thenReturn(Optional.of(alreadyInactive));

        assertThrows(ValidationException.class, () -> serviceToTest.handle(new DeactivatePositionCommand(ID)));

        verify(findById).findById(ID);
        verify(savePosition, never()).save(any());
        verifyNoMoreInteractions(findById, savePosition);
    }
}
