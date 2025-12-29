package com.sprintforge.employee.position.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.position.application.port.in.command.*;
import com.sprintforge.employee.position.application.port.in.query.GetAllPositionsQuery;
import com.sprintforge.employee.position.application.port.in.query.GetPositionByIdQuery;
import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.CreatePositionRequestDTO;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.PositionResponseDTO;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.UpdatePositionDetailRequestDTO;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PositionRestMapperTest {

    private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    private static final String NAME = "Developer";
    private static final String DESCRIPTION = "Backend developer";

    @Test
    void shouldMapToGetAllQuery() {
        GetAllPositionsQuery query = PositionRestMapper.toQuery("dev", true, false);

        assertAll(
                () -> assertEquals("dev", query.searchTerm()),
                () -> assertEquals(true, query.isActive()),
                () -> assertEquals(false, query.isDeleted())
        );
    }

    @Test
    void shouldMapToGetByIdQuery() {
        GetPositionByIdQuery query = PositionRestMapper.toQuery(ID);

        assertEquals(ID, query.id());
    }

    @Test
    void shouldMapToCreateCommand() {
        CreatePositionRequestDTO dto = new CreatePositionRequestDTO(NAME, DESCRIPTION);

        CreatePositionCommand command = PositionRestMapper.toCreateCommand(dto);

        assertAll(
                () -> assertEquals(NAME, command.name()),
                () -> assertEquals(DESCRIPTION, command.description())
        );
    }

    @Test
    void shouldMapToUpdateCommand() {
        UpdatePositionDetailRequestDTO dto = new UpdatePositionDetailRequestDTO("Senior Developer", "Updated desc");

        UpdatePositionDetailCommand command = PositionRestMapper.toUpdateCommand(ID, dto);

        assertAll(
                () -> assertEquals(ID, command.id()),
                () -> assertEquals(dto.name(), command.name()),
                () -> assertEquals(dto.description(), command.description())
        );
    }

    @Test
    void shouldMapToActivateCommand() {
        ActivatePositionCommand command = PositionRestMapper.toActivateCommand(ID);

        assertEquals(ID, command.id());
    }

    @Test
    void shouldMapToDeactivateCommand() {
        DeactivatePositionCommand command = PositionRestMapper.toDeactivateCommand(ID);

        assertEquals(ID, command.id());
    }

    @Test
    void shouldMapToDeleteCommand() {
        DeletePositionCommand command = PositionRestMapper.toDeleteCommand(ID);

        assertEquals(ID, command.id());
    }

    @Test
    void shouldMapToResponseDTO() {
        Position position = new Position(ID, NAME, DESCRIPTION, true, false, NOW, NOW);

        PositionResponseDTO dto = PositionRestMapper.toResponse(position);

        assertAll(
                () -> assertEquals(ID, dto.id()),
                () -> assertEquals(NAME, dto.name()),
                () -> assertEquals(DESCRIPTION, dto.description()),
                () -> assertTrue(dto.isActive()),
                () -> assertFalse(dto.isDeleted()),
                () -> assertEquals(NOW, dto.createdAt()),
                () -> assertEquals(NOW, dto.updatedAt())
        );
    }
}
