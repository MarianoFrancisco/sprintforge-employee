package com.sprintforge.employee.position.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.entity.PositionEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PositionEntityMapperTest {

    private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    private static final String NAME = "Developer";
    private static final String DESCRIPTION = "Backend developer";

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        assertNull(PositionEntityMapper.toDomain(null));
    }

    @Test
    void shouldReturnNullWhenDomainIsNull() {
        assertNull(PositionEntityMapper.toEntity(null));
    }

    @Test
    void shouldMapEntityToDomain() {
        PositionEntity entity = PositionEntity.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .isActive(true)
                .isDeleted(false)
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();

        Position domain = PositionEntityMapper.toDomain(entity);

        assertAll(
                () -> assertNotNull(domain),
                () -> assertEquals(ID, domain.getId().value()),
                () -> assertEquals(NAME, domain.getName().value()),
                () -> assertEquals(DESCRIPTION, domain.getDescription().value()),
                () -> assertTrue(domain.isActive()),
                () -> assertFalse(domain.isDeleted()),
                () -> assertEquals(NOW, domain.getCreatedAt()),
                () -> assertEquals(NOW, domain.getUpdatedAt())
        );
    }

    @Test
    void shouldMapDomainToEntity() {
        Position domain = new Position(ID, NAME, DESCRIPTION, true, false, NOW, NOW);

        PositionEntity entity = PositionEntityMapper.toEntity(domain);

        assertAll(
                () -> assertNotNull(entity),
                () -> assertEquals(ID, entity.getId()),
                () -> assertEquals(NAME, entity.getName()),
                () -> assertEquals(DESCRIPTION, entity.getDescription()),
                () -> assertTrue(entity.isActive()),
                () -> assertFalse(entity.isDeleted()),
                () -> assertEquals(NOW, entity.getCreatedAt()),
                () -> assertEquals(NOW, entity.getUpdatedAt())
        );
    }
}
