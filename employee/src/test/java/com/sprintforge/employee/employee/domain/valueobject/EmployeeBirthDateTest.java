package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeBirthDateTest {

    @Test
    void shouldCreateSuccessfullyWhenAdult() {
        LocalDate birthDate = LocalDate.now().minusYears(18);
        EmployeeBirthDate vo = new EmployeeBirthDate(birthDate);
        assertEquals(birthDate, vo.value());
    }

    @Test
    void shouldThrowWhenNull() {
        assertThrows(ValidationException.class, () -> new EmployeeBirthDate(null));
    }

    @Test
    void shouldThrowWhenFuture() {
        LocalDate future = LocalDate.now().plusDays(1);
        assertThrows(ValidationException.class, () -> new EmployeeBirthDate(future));
    }

    @Test
    void shouldThrowWhenUnderage() {
        LocalDate underAge = LocalDate.now().minusYears(17);
        assertThrows(ValidationException.class, () -> new EmployeeBirthDate(underAge));
    }
}
