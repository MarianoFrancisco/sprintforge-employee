package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeLastNameTest {

    @Test
    void shouldCreateAndTrim() {
        EmployeeLastName lastName = new EmployeeLastName("   Doe  ");
        assertEquals("Doe", lastName.value());
    }

    @Test
    void shouldThrowWhenNullOrBlank() {
        assertAll(
                () -> assertThrows(ValidationException.class, () -> new EmployeeLastName(null)),
                () -> assertThrows(ValidationException.class, () -> new EmployeeLastName("   "))
        );
    }

    @Test
    void shouldThrowWhenTooLong() {
        String tooLong = "a".repeat(101);
        assertThrows(ValidationException.class, () -> new EmployeeLastName(tooLong));
    }
}
