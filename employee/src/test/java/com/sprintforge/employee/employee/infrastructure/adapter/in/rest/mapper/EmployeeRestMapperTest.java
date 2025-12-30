package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.employee.employee.application.port.in.command.HireEmployeeCommand;
import com.sprintforge.employee.employee.application.port.in.command.ValidateEmployeesCommand;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeesByIdsQuery;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.domain.valueobject.*;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.GetEmployeesByIdsRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.HireEmployeeRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.ValidateEmployeesRequestDTO;
import com.sprintforge.employee.position.domain.Position;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRestMapperTest {

    private static final UUID EMPLOYEE_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID POSITION_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    @Test
    void shouldMapValidateEmployeesCommand() {
        Set<UUID> ids = Set.of(EMPLOYEE_ID);
        ValidateEmployeesRequestDTO dto = new ValidateEmployeesRequestDTO(ids);

        ValidateEmployeesCommand command = EmployeeRestMapper.toValidateCommnd(dto);

        assertEquals(ids, command.ids());
    }

    @Test
    void shouldMapHireCommandWithoutImage() {
        HireEmployeeRequestDTO dto = new HireEmployeeRequestDTO(
                "1234567890101",
                "john.doe@test.com",
                "John",
                "Doe",
                "55556666",
                LocalDate.now().minusYears(25),
                POSITION_ID,
                EmployeeWorkloadType.FULL_TIME,
                new BigDecimal("5000.00"),
                null,
                LocalDate.now().minusDays(10),
                "hire"
        );

        HireEmployeeCommand command = EmployeeRestMapper.toHireCommand(dto);

        assertAll(
                () -> assertEquals(dto.cui(), command.cui()),
                () -> assertEquals(dto.email(), command.email()),
                () -> assertEquals(dto.firstName(), command.firstName()),
                () -> assertEquals(dto.lastName(), command.lastName()),
                () -> assertEquals(dto.phoneNumber(), command.phoneNumber()),
                () -> assertEquals(dto.birthDate(), command.birthDate()),
                () -> assertEquals(dto.positionId(), command.positionId()),
                () -> assertEquals(dto.workloadType(), command.workloadType()),
                () -> assertEquals(dto.salary(), command.salary()),
                () -> assertTrue(command.profileImage().isEmpty()),
                () -> assertEquals(dto.startDate(), command.startDate()),
                () -> assertEquals(dto.notes(), command.notes())
        );
    }

    @Test
    void shouldMapHireCommandWithImage() {
        MockMultipartFile file = new MockMultipartFile(
                "profileImage",
                "pic.png",
                "image/png",
                "img".getBytes()
        );

        HireEmployeeRequestDTO dto = new HireEmployeeRequestDTO(
                "1234567890101",
                "john.doe@test.com",
                "John",
                "Doe",
                "55556666",
                LocalDate.now().minusYears(25),
                POSITION_ID,
                EmployeeWorkloadType.FULL_TIME,
                new BigDecimal("5000.00"),
                file,
                LocalDate.now().minusDays(10),
                "hire"
        );

        HireEmployeeCommand command = EmployeeRestMapper.toHireCommand(dto);

        assertTrue(command.profileImage().isPresent());
        assertEquals(file.getSize(), command.profileImage().get().size());
        assertEquals(file.getContentType(), command.profileImage().get().contentType());
    }

    @Test
    void shouldMapEmployeeToResponse() {
        Position position = new Position(
                POSITION_ID,
                "Developer",
                "Backend",
                true,
                false,
                NOW,
                NOW
        );

        Employee employee = Employee.rehydrate(
                EMPLOYEE_ID,
                "1234567890101",
                "john.doe@test.com",
                "John",
                "Doe",
                "John Doe",
                "55556666",
                LocalDate.now().minusYears(25),
                position,
                EmployeeWorkloadType.FULL_TIME,
                new BigDecimal("5000.00"),
                "imgKey",
                EmployeeStatus.ACTIVE
        );

        EmployeeResponseDTO dto = EmployeeRestMapper.toResponse(employee);

        assertAll(
                () -> assertEquals(EMPLOYEE_ID, dto.id()),
                () -> assertEquals("1234567890101", dto.cui()),
                () -> assertEquals("john.doe@test.com", dto.email()),
                () -> assertEquals("John", dto.firstName()),
                () -> assertEquals("Doe", dto.lastName()),
                () -> assertEquals("John Doe", dto.fullName()),
                () -> assertEquals(EmployeeStatus.ACTIVE, dto.status()),
                () -> assertEquals(POSITION_ID, dto.position().id())
        );
    }
}
