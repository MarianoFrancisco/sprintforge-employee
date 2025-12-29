package com.sprintforge.employee.position.infrastructure.adapter.in.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprintforge.employee.position.application.port.in.command.*;
import com.sprintforge.employee.position.application.port.in.query.*;
import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.CreatePositionRequestDTO;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.UpdatePositionDetailRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PositionControllerTest {

    private static final String BASE_URL = "/api/v1/position";

    private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    private static final String NAME = "Developer";
    private static final String DESCRIPTION = "Backend developer";

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private GetAllPositions getAllPositions;
    @Mock
    private GetPositionById getPositionById;
    @Mock
    private CreatePosition createPosition;
    @Mock
    private UpdatePositionDetail updatePositionDetail;
    @Mock
    private ActivatePosition activatePosition;
    @Mock
    private DeactivatePosition deactivatePosition;
    @Mock
    private DeletePosition deletePosition;

    @InjectMocks
    private PositionController controller;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void shouldGetAllPositions() throws Exception {
        Position p1 = new Position(ID, NAME, DESCRIPTION, true, false, NOW, NOW);
        when(getAllPositions.handle(any(GetAllPositionsQuery.class))).thenReturn(List.of(p1));

        mockMvc.perform(get(BASE_URL)
                        .param("searchTerm", "dev")
                        .param("isActive", "true")
                        .param("isDeleted", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID.toString()))
                .andExpect(jsonPath("$[0].name").value(NAME))
                .andExpect(jsonPath("$[0].description").value(DESCRIPTION))
                .andExpect(jsonPath("$[0].isActive").value(true))
                .andExpect(jsonPath("$[0].isDeleted").value(false));

        verify(getAllPositions).handle(any(GetAllPositionsQuery.class));
        verifyNoMoreInteractions(getAllPositions);
    }

    @Test
    void shouldGetPositionById() throws Exception {
        Position p = new Position(ID, NAME, DESCRIPTION, true, false, NOW, NOW);
        when(getPositionById.handle(any(GetPositionByIdQuery.class))).thenReturn(p);

        mockMvc.perform(get(BASE_URL + "/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.name").value(NAME));

        verify(getPositionById).handle(any(GetPositionByIdQuery.class));
        verifyNoMoreInteractions(getPositionById);
    }

    @Test
    void shouldCreatePosition() throws Exception {
        CreatePositionRequestDTO dto = new CreatePositionRequestDTO(NAME, DESCRIPTION);

        Position saved = new Position(ID, NAME, DESCRIPTION, true, false, NOW, NOW);
        when(createPosition.handle(any(CreatePositionCommand.class))).thenReturn(saved);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.name").value(NAME));

        verify(createPosition).handle(any(CreatePositionCommand.class));
        verifyNoMoreInteractions(createPosition);
    }

    @Test
    void shouldUpdatePositionDetails() throws Exception {
        UpdatePositionDetailRequestDTO dto = new UpdatePositionDetailRequestDTO("Senior Developer", "Updated desc");

        Position updated = new Position(ID, dto.name(), dto.description(), true, false, NOW, NOW);
        when(updatePositionDetail.handle(any(UpdatePositionDetailCommand.class))).thenReturn(updated);

        mockMvc.perform(patch(BASE_URL + "/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.name").value(dto.name()))
                .andExpect(jsonPath("$.description").value(dto.description()));

        verify(updatePositionDetail).handle(any(UpdatePositionDetailCommand.class));
        verifyNoMoreInteractions(updatePositionDetail);
    }

    @Test
    void shouldActivatePosition() throws Exception {
        Position activated = new Position(ID, NAME, DESCRIPTION, true, false, NOW, NOW);
        when(activatePosition.handle(any(ActivatePositionCommand.class))).thenReturn(activated);

        mockMvc.perform(patch(BASE_URL + "/{id}:activate", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isActive").value(true));

        verify(activatePosition).handle(any(ActivatePositionCommand.class));
        verifyNoMoreInteractions(activatePosition);
    }

    @Test
    void shouldDeactivatePosition() throws Exception {
        Position deactivated = new Position(ID, NAME, DESCRIPTION, false, false, NOW, NOW);
        when(deactivatePosition.handle(any(DeactivatePositionCommand.class))).thenReturn(deactivated);

        mockMvc.perform(patch(BASE_URL + "/{id}:deactivate", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isActive").value(false));

        verify(deactivatePosition).handle(any(DeactivatePositionCommand.class));
        verifyNoMoreInteractions(deactivatePosition);
    }

    @Test
    void shouldDeletePosition() throws Exception {
        doNothing().when(deletePosition).handle(any(DeletePositionCommand.class));

        mockMvc.perform(delete(BASE_URL + "/{id}", ID))
                .andExpect(status().isNoContent());

        verify(deletePosition).handle(any(DeletePositionCommand.class));
        verifyNoMoreInteractions(deletePosition);
    }
}
