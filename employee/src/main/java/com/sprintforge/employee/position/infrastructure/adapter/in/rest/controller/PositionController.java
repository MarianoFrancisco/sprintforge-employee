package com.sprintforge.employee.position.infrastructure.adapter.in.rest.controller;

import com.sprintforge.employee.position.application.port.in.command.ActivatePosition;
import com.sprintforge.employee.position.application.port.in.command.CreatePosition;
import com.sprintforge.employee.position.application.port.in.command.DeactivatePosition;
import com.sprintforge.employee.position.application.port.in.command.DeletePosition;
import com.sprintforge.employee.position.application.port.in.query.GetAllPositions;
import com.sprintforge.employee.position.application.port.in.command.UpdatePositionDetail;
import com.sprintforge.employee.position.application.port.in.query.GetPositionById;
import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.CreatePositionRequestDTO;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.PositionResponseDTO;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.dto.UpdatePositionDetailRequestDTO;
import com.sprintforge.employee.position.infrastructure.adapter.in.rest.mapper.PositionRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/position")
@RequiredArgsConstructor
public class PositionController {

    private final GetAllPositions getAllPositions;
    private final GetPositionById getPositionById;
    private final CreatePosition createPosition;
    private final UpdatePositionDetail updatePositionDetail;
    private final ActivatePosition activatePosition;
    private final DeactivatePosition deactivatePosition;
    private final DeletePosition deletePosition;

    @GetMapping
    public List<PositionResponseDTO> getAll(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        List<Position> positions = getAllPositions.handle(
                PositionRestMapper.toQuery(
                        searchTerm,
                        isActive,
                        isDeleted
                )
        );
        return positions.stream()
                .map(PositionRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public PositionResponseDTO getById(@PathVariable UUID id) {
        Position position = getPositionById.handle(
                PositionRestMapper.toQuery(id)
        );
        return PositionRestMapper.toResponse(position);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public PositionResponseDTO create(
            @RequestBody @Valid CreatePositionRequestDTO dto
    ) {
        Position saved = createPosition.handle(
                PositionRestMapper.toCreateCommand(dto)
        );
        return PositionRestMapper.toResponse(saved);
    }

    @PatchMapping
    public PositionResponseDTO updateDetails(
            @RequestParam UUID id,
            @RequestBody @Valid UpdatePositionDetailRequestDTO dto
    ) {
        Position updated = updatePositionDetail.handle(
                PositionRestMapper.toUpdateCommand(
                        id,
                        dto
                )
        );
        return PositionRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}:activate")
    public PositionResponseDTO activate(@PathVariable UUID id) {
        Position activated = activatePosition.handle(
                PositionRestMapper.toActivateCommand(id)
        );
        return PositionRestMapper.toResponse(activated);
    }

    @PatchMapping("/{id}:deactivate")
    public PositionResponseDTO deactivate(@PathVariable UUID id) {
        Position deactivated = deactivatePosition.handle(
                PositionRestMapper.toDeactivateCommand(id)
        );
        return PositionRestMapper.toResponse(deactivated);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void delete(@RequestParam UUID id) {
        deletePosition.handle(
                PositionRestMapper.toDeleteCommand(id)
        );
    }
}
