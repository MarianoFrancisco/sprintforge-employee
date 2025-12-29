package com.sprintforge.employee.position.infrastructure.adapter.out.persistence;

import com.sprintforge.employee.position.application.port.in.query.GetAllPositionsQuery;
import com.sprintforge.employee.position.domain.Position;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.entity.PositionEntity;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.repository.PositionJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PositionRepositoryTest {

    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final String NAME = "Developer";
    private static final String DESCRIPTION = "Backend developer";

    @Mock
    private PositionJpaRepository jpaRepository;

    @InjectMocks
    private PositionRepository repository;

    private PositionEntity entity;

    @BeforeEach
    void setUp() {
        entity = PositionEntity.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .isActive(true)
                .isDeleted(false)
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();
    }

    @Test
    void shouldSavePosition() {
        Position domain = new Position(ID, NAME, DESCRIPTION, true, false, NOW, NOW);
        when(jpaRepository.save(any(PositionEntity.class))).thenReturn(entity);

        Position saved = repository.save(domain);

        assertThat(saved.getId().value()).isEqualTo(ID);
        assertThat(saved.getName().value()).isEqualTo(NAME);

        verify(jpaRepository).save(any(PositionEntity.class));
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    void shouldFindById() {
        when(jpaRepository.findById(ID)).thenReturn(Optional.of(entity));

        Optional<Position> result = repository.findById(ID);

        assertThat(result).isPresent();
        assertThat(result.get().getId().value()).isEqualTo(ID);

        verify(jpaRepository).findById(ID);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    void shouldReturnEmptyWhenIdNotExists() {
        when(jpaRepository.findById(ID)).thenReturn(Optional.empty());

        Optional<Position> result = repository.findById(ID);

        assertThat(result).isEmpty();
        verify(jpaRepository).findById(ID);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    void shouldCheckExistsByName() {
        when(jpaRepository.existsByName(NAME)).thenReturn(true);

        boolean exists = repository.existsByName(NAME);

        assertThat(exists).isTrue();
        verify(jpaRepository).existsByName(NAME);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    void shouldCheckExistsByNameAndIdNot() {
        when(jpaRepository.existsByNameAndIdNot(NAME, ID)).thenReturn(true);

        boolean exists = repository.existsByNameAndIdNot(NAME, ID);

        assertThat(exists).isTrue();
        verify(jpaRepository).existsByNameAndIdNot(NAME, ID);
        verifyNoMoreInteractions(jpaRepository);
    }

    @Test
    void shouldFindAllWithFilters() {
        when(jpaRepository.findAll(any(Specification.class))).thenReturn(List.of(entity));

        List<Position> result = repository.findAll(new GetAllPositionsQuery("dev", true, false));

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId().value()).isEqualTo(ID);

        verify(jpaRepository).findAll(any(Specification.class));
        verifyNoMoreInteractions(jpaRepository);
    }
}
