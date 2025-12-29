package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeFirstNameTest {

    @Test
    void shouldCreateAndTrim() {
        EmployeeFirstName name = new EmployeeFirstName("   John  ");
        assertEquals("John", name.value());
    }

    @Test
    void shouldThrowWhenNullOrBlank() {
        assertAll(
                () -> assertThrows(ValidationException.class, () -> new EmployeeFirstName(null)),
                () -> assertThrows(ValidationException.class, () -> new EmployeeFirstName("   "))
        );
    }

    @Test
    void shouldThrowWhenTooLong() {
        String tooLong = "a".repeat(101);
        assertThrows(ValidationException.class, () -> new EmployeeFirstName(tooLong));
    }
}
