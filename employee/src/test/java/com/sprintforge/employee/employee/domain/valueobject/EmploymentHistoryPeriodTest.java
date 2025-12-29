package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmploymentHistoryPeriodTest {

    @Test
    void shouldStartSuccessfully() {
        LocalDate start = LocalDate.now().minusDays(1);

        EmploymentHistoryPeriod period = EmploymentHistoryPeriod.start(start);

        assertAll(
                () -> assertEquals(start, period.getStartDate()),
                () -> assertNull(period.getEndDate()),
                () -> assertTrue(period.isActive())
        );
    }

    @Test
    void shouldThrowWhenStartIsNull() {
        assertThrows(ValidationException.class, () -> EmploymentHistoryPeriod.start(null));
    }

    @Test
    void shouldThrowWhenStartIsFuture() {
        LocalDate future = LocalDate.now().plusDays(1);
        assertThrows(ValidationException.class, () -> EmploymentHistoryPeriod.start(future));
    }

    @Test
    void shouldCreateWithEndSuccessfully() {
        LocalDate start = LocalDate.now().minusDays(10);
        LocalDate end = LocalDate.now().minusDays(1);

        EmploymentHistoryPeriod period = EmploymentHistoryPeriod.of(start, end);

        assertAll(
                () -> assertEquals(start, period.getStartDate()),
                () -> assertEquals(end, period.getEndDate()),
                () -> assertFalse(period.isActive())
        );
    }

    @Test
    void shouldThrowWhenEndBeforeStart() {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now().minusDays(10);

        assertThrows(ValidationException.class, () -> EmploymentHistoryPeriod.of(start, end));
    }

    @Test
    void shouldEndSuccessfully() {
        LocalDate start = LocalDate.now().minusDays(10);
        EmploymentHistoryPeriod period = EmploymentHistoryPeriod.start(start);

        LocalDate end = LocalDate.now().minusDays(1);
        period.end(end);

        assertAll(
                () -> assertEquals(end, period.getEndDate()),
                () -> assertFalse(period.isActive())
        );
    }

    @Test
    void shouldThrowWhenEndingTwice() {
        LocalDate start = LocalDate.now().minusDays(10);
        EmploymentHistoryPeriod period = EmploymentHistoryPeriod.start(start);

        period.end(LocalDate.now().minusDays(1));

        assertThrows(ValidationException.class, () -> period.end(LocalDate.now()));
    }
}
