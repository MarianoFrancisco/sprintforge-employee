package com.sprintforge.employee.position.application.service;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.employee.position.application.exception.DuplicatePositionException;
import com.sprintforge.employee.position.application.exception.PositionNotFoundException;
import com.sprintforge.employee.position.application.port.in.command.UpdatePositionDetailCommand;
import com.sprintforge.employee.position.application.port.out.persistence.ExistsPositionByNameAndIdNot;
import com.sprintforge.employee.position.application.port.out.persistence.FindPositionById;
import com.sprintforge.employee.position.application.port.out.persistence.SavePosition;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePositionDetailImplTest {

    private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final String OLD_NAME = "Developer";
    private static final String OLD_DESC = "Backend developer";
    private static final String NEW_NAME = "Senior Developer";
    private static final String NEW_DESC = "Backend senior developer";
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    @Mock
    private ExistsPositionByNameAndIdNot existsByNameAndIdNot;

    @Mock
    private FindPositionById findById;

    @Mock
    private SavePosition savePosition;

    @InjectMocks
    private UpdatePositionDetailImpl serviceToTest;

    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(ID, OLD_NAME, OLD_DESC, true, false, NOW, NOW);
    }

    @Test
    void shouldUpdateDetailsSuccessfully() {
        when(findById.findById(ID)).thenReturn(Optional.of(position));
        when(existsByNameAndIdNot.existsByNameAndIdNot(NEW_NAME, ID)).thenReturn(false);

        ArgumentCaptor<Position> captor = ArgumentCaptor.forClass(Position.class);
        when(savePosition.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        Position result = serviceToTest.handle(new UpdatePositionDetailCommand(ID, NEW_NAME, NEW_DESC));

        assertAll(
                () -> assertEquals(NEW_NAME, result.getName().value()),
                () -> assertEquals(NEW_DESC, result.getDescription().value())
        );

        Position persisted = captor.getValue();
        assertEquals(NEW_NAME, persisted.getName().value());

        verify(findById).findById(ID);
        verify(existsByNameAndIdNot).existsByNameAndIdNot(NEW_NAME, ID);
        verify(savePosition).save(any(Position.class));
        verifyNoMoreInteractions(findById, existsByNameAndIdNot, savePosition);
    }

    @Test
    void shouldThrowPositionNotFoundExceptionWhenNotExists() {
        when(findById.findById(ID)).thenReturn(Optional.empty());

        assertThrows(PositionNotFoundException.class,
                () -> serviceToTest.handle(new UpdatePositionDetailCommand(ID, NEW_NAME, NEW_DESC)));

        verify(findById).findById(ID);
        verify(savePosition, never()).save(any());
        verifyNoMoreInteractions(findById, existsByNameAndIdNot, savePosition);
    }

    @Test
    void shouldThrowDuplicatePositionExceptionWhenNameBelongsToAnotherPosition() {
        when(findById.findById(ID)).thenReturn(Optional.of(position));
        when(existsByNameAndIdNot.existsByNameAndIdNot(NEW_NAME, ID)).thenReturn(true);

        assertThrows(DuplicatePositionException.class,
                () -> serviceToTest.handle(new UpdatePositionDetailCommand(ID, NEW_NAME, NEW_DESC)));

        verify(findById).findById(ID);
        verify(existsByNameAndIdNot).existsByNameAndIdNot(NEW_NAME, ID);
        verify(savePosition, never()).save(any());
        verifyNoMoreInteractions(findById, existsByNameAndIdNot, savePosition);
    }

    @Test
    void shouldNotCheckDuplicateWhenNameDoesNotChange() {
        when(findById.findById(ID)).thenReturn(Optional.of(position));
        when(savePosition.save(any(Position.class))).thenAnswer(inv -> inv.getArgument(0));

        Position result = serviceToTest.handle(new UpdatePositionDetailCommand(ID, OLD_NAME, NEW_DESC));

        assertEquals(OLD_NAME, result.getName().value());
        assertEquals(NEW_DESC, result.getDescription().value());

        verify(findById).findById(ID);
        verify(existsByNameAndIdNot, never()).existsByNameAndIdNot(anyString(), any());
        verify(savePosition).save(position);
        verifyNoMoreInteractions(findById, existsByNameAndIdNot, savePosition);
    }
}
