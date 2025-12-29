package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence;

import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.application.port.in.query.GetHiringHistoryReportQuery;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminationHistoryReportQuery;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.projection.EmploymentHistoryEmployeeView;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.repository.EmploymentHistoryReportJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmploymentHistoryReportRepositoryTest {

    @Mock
    private EmploymentHistoryReportJpaRepository jpa;

    @InjectMocks
    private EmploymentHistoryReportRepository repo;

    @Test
    void shouldLoadHiredReport() {
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to = LocalDate.of(2025, 1, 31);

        EmploymentHistoryEmployeeView view = new EmploymentHistoryEmployeeView() {
            @Override
            public UUID getEmployeeId() {
                return UUID.randomUUID();
            }

            @Override
            public String getCui() {
                return "123";
            }

            @Override
            public String getFullName() {
                return "John Doe";
            }

            @Override
            public LocalDate getDate() {
                return LocalDate.of(2025, 1, 10);
            }
        };

        when(jpa.hiredEmployees(from, to)).thenReturn(List.of(view));

        EmployeesByEmploymentHistoryReportResult result = repo.loadHired(new GetHiringHistoryReportQuery(from, to));

        assertEquals(1, result.total());
        assertEquals("123", result.employees().getFirst().cui());
        verify(jpa).hiredEmployees(from, to);
    }

    @Test
    void shouldLoadTerminatedReport() {
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to = LocalDate.of(2025, 1, 31);

        when(jpa.terminatedEmployees(from, to)).thenReturn(List.of());

        EmployeesByEmploymentHistoryReportResult result = repo.loadTerminated(new GetTerminationHistoryReportQuery(from, to));

        assertEquals(0, result.total());
        verify(jpa).terminatedEmployees(from, to);
    }
}
