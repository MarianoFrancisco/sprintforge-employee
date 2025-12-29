package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.common.application.port.result.EmployeeRow;
import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.HiredEmployeeHistoryRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.TerminatedEmployeeHistoryRequestDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InternalEmployeeHistoryRestMapperTest {

    @Test
    void shouldMapHiringDtoToQuery() {
        HiredEmployeeHistoryRequestDTO dto = new HiredEmployeeHistoryRequestDTO(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 31)
        );

        var query = InternalEmployeeHistoryRestMapper.toHiringHistoryReportQuery(dto);

        assertAll(
                () -> assertEquals(dto.from(), query.from()),
                () -> assertEquals(dto.to(), query.to())
        );
    }

    @Test
    void shouldMapTerminationDtoToQuery() {
        TerminatedEmployeeHistoryRequestDTO dto = new TerminatedEmployeeHistoryRequestDTO(
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 2, 28)
        );

        var query = InternalEmployeeHistoryRestMapper.toTerminationHistoryReportQuery(dto);

        assertAll(
                () -> assertEquals(dto.from(), query.from()),
                () -> assertEquals(dto.to(), query.to())
        );
    }

    @Test
    void shouldMapResultToResponse() {
        EmployeesByEmploymentHistoryReportResult result = new EmployeesByEmploymentHistoryReportResult(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 31),
                1L,
                List.of(new EmployeeRow(
                        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                        "1234567890101",
                        "John Doe",
                        LocalDate.of(2025, 1, 15)
                ))
        );

        var response = InternalEmployeeHistoryRestMapper.fromResult(result);

        assertAll(
                () -> assertEquals(result.from(), response.from()),
                () -> assertEquals(result.to(), response.to()),
                () -> assertEquals(result.total(), response.total()),
                () -> assertEquals(1, response.employees().size()),
                () -> assertEquals("1234567890101", response.employees().getFirst().cui())
        );
    }
}
