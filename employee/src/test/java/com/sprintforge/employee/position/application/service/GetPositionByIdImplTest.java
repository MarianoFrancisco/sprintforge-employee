package com.sprintforge.employee.position.application.service;

import com.sprintforge.employee.position.application.exception.PositionNotFoundException;
import com.sprintforge.employee.position.application.port.in.query.GetPositionByIdQuery;
import com.sprintforge.employee.position.application.port.out.persistence.FindPositionById;
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
class GetPositionByIdImplTest {

    private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final String NAME = "Developer";
    private static final String DESCRIPTION = "Backend developer";

    @Mock
    private FindPositionById findById;

    @InjectMocks
    private GetPositionByIdImpl serviceToTest;

    private Position position;

    @BeforeEach
    void setUp() {
        Instant now = Instant.parse("2025-01-01T00:00:00Z");
        position = new Position(ID, NAME, DESCRIPTION, true, false, now, now);
    }

    @Test
    void shouldReturnPositionWhenExists() {
        when(findById.findById(ID)).thenReturn(Optional.of(position));

        Position result = serviceToTest.handle(new GetPositionByIdQuery(ID));

        assertNotNull(result);
        assertEquals(ID, result.getId().value());

        verify(findById).findById(ID);
        verifyNoMoreInteractions(findById);
    }

    @Test
    void shouldThrowPositionNotFoundExceptionWhenNotExists() {
        when(findById.findById(ID)).thenReturn(Optional.empty());

        assertThrows(PositionNotFoundException.class, () -> serviceToTest.handle(new GetPositionByIdQuery(ID)));

        verify(findById).findById(ID);
        verifyNoMoreInteractions(findById);
    }
}
