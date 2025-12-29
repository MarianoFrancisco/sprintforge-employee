package com.sprintforge.employee.position.application.service;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.employee.position.application.exception.PositionNotFoundException;
import com.sprintforge.employee.position.application.port.in.command.DeletePositionCommand;
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
class DeletePositionImplTest {

    private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final String NAME = "Developer";
    private static final String DESCRIPTION = "Backend developer";
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    @Mock
    private FindPositionById findById;

    @Mock
    private SavePosition savePosition;

    @InjectMocks
    private DeletePositionImpl serviceToTest;

    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(ID, NAME, DESCRIPTION, true, false, NOW, NOW);
    }

    @Test
    void shouldDeletePositionSuccessfully() {
        when(findById.findById(ID)).thenReturn(Optional.of(position));

        serviceToTest.handle(new DeletePositionCommand(ID));

        assertTrue(position.isDeleted());
        verify(findById).findById(ID);
        verify(savePosition).save(position);
        verifyNoMoreInteractions(findById, savePosition);
    }

    @Test
    void shouldThrowPositionNotFoundExceptionWhenNotExists() {
        when(findById.findById(ID)).thenReturn(Optional.empty());

        assertThrows(PositionNotFoundException.class, () -> serviceToTest.handle(new DeletePositionCommand(ID)));

        verify(findById).findById(ID);
        verify(savePosition, never()).save(any());
        verifyNoMoreInteractions(findById, savePosition);
    }

    @Test
    void shouldPropagateValidationExceptionWhenAlreadyDeleted() {
        Position deleted = new Position(ID, NAME, DESCRIPTION, true, true, NOW, NOW);
        when(findById.findById(ID)).thenReturn(Optional.of(deleted));

        assertThrows(ValidationException.class, () -> serviceToTest.handle(new DeletePositionCommand(ID)));

        verify(findById).findById(ID);
        verify(savePosition, never()).save(any());
        verifyNoMoreInteractions(findById, savePosition);
    }
}
