package com.sprintforge.employee.position.application.service;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.employee.position.application.exception.PositionNotFoundException;
import com.sprintforge.employee.position.application.port.in.command.ActivatePositionCommand;
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
class ActivatePositionImplTest {

    private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final String NAME = "Developer";
    private static final String DESCRIPTION = "Backend developer";
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    @Mock
    private FindPositionById findById;

    @Mock
    private SavePosition savePosition;

    @InjectMocks
    private ActivatePositionImpl serviceToTest;

    private Position inactivePosition;

    @BeforeEach
    void setUp() {
        inactivePosition = new Position(ID, NAME, DESCRIPTION, false, false, NOW, NOW);
    }

    @Test
    void shouldActivatePositionSuccessfully() {
        when(findById.findById(ID)).thenReturn(Optional.of(inactivePosition));
        when(savePosition.save(any(Position.class))).thenAnswer(inv -> inv.getArgument(0));

        Position result = serviceToTest.handle(new ActivatePositionCommand(ID));

        assertTrue(result.isActive());
        verify(findById).findById(ID);
        verify(savePosition).save(inactivePosition);
        verifyNoMoreInteractions(findById, savePosition);
    }

    @Test
    void shouldThrowPositionNotFoundExceptionWhenNotExists() {
        when(findById.findById(ID)).thenReturn(Optional.empty());

        assertThrows(PositionNotFoundException.class, () -> serviceToTest.handle(new ActivatePositionCommand(ID)));

        verify(findById).findById(ID);
        verify(savePosition, never()).save(any());
        verifyNoMoreInteractions(findById, savePosition);
    }

    @Test
    void shouldPropagateValidationExceptionWhenAlreadyActive() {
        Position alreadyActive = new Position(ID, NAME, DESCRIPTION, true, false, NOW, NOW);
        when(findById.findById(ID)).thenReturn(Optional.of(alreadyActive));

        assertThrows(ValidationException.class, () -> serviceToTest.handle(new ActivatePositionCommand(ID)));

        verify(findById).findById(ID);
        verify(savePosition, never()).save(any());
        verifyNoMoreInteractions(findById, savePosition);
    }
}
