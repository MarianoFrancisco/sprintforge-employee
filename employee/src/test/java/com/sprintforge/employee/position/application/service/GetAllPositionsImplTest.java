package com.sprintforge.employee.position.application.service;

import com.sprintforge.employee.position.application.port.in.query.GetAllPositionsQuery;
import com.sprintforge.employee.position.application.port.out.persistence.FindAllPositions;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllPositionsImplTest {

    private static final UUID ID_1 = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID ID_2 = UUID.fromString("22222222-2222-2222-2222-222222222222");
    private static final String NAME_1 = "Developer";
    private static final String NAME_2 = "QA";
    private static final String DESC = "Role";

    @Mock
    private FindAllPositions findAllPositions;

    @InjectMocks
    private GetAllPositionsImpl serviceToTest;

    private Position p1;
    private Position p2;

    @BeforeEach
    void setUp() {
        Instant now = Instant.parse("2025-01-01T00:00:00Z");
        p1 = new Position(ID_1, NAME_1, DESC, true, false, now, now);
        p2 = new Position(ID_2, NAME_2, DESC, false, false, now, now);
    }

    @Test
    void shouldReturnAllPositions() {
        GetAllPositionsQuery query = new GetAllPositionsQuery(null, null, null);

        when(findAllPositions.findAll(query)).thenReturn(List.of(p1, p2));

        List<Position> result = serviceToTest.handle(query);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(ID_1, result.get(0).getId().value()),
                () -> assertEquals(ID_2, result.get(1).getId().value())
        );

        verify(findAllPositions).findAll(query);
        verifyNoMoreInteractions(findAllPositions);
    }
}
