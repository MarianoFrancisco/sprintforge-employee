package com.sprintforge.employee.employee.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeProfileImageKeyTest {

    @Test
    void shouldAllowNull() {
        EmployeeProfileImageKey key = new EmployeeProfileImageKey(null);
        assertTrue(key.isEmpty());
        assertNull(key.value());
    }

    @Test
    void shouldGenerateRandomKey() {
        EmployeeProfileImageKey key = EmployeeProfileImageKey.generate();
        assertNotNull(key.value());
        assertFalse(key.isEmpty());
    }

    @Test
    void shouldCreateEmpty() {
        EmployeeProfileImageKey key = EmployeeProfileImageKey.empty();
        assertTrue(key.isEmpty());
    }

    @Test
    void shouldThrowWhenTooLong() {
        String tooLong = "a".repeat(301);
        assertThrows(ValidationException.class, () -> new EmployeeProfileImageKey(tooLong));
    }
}
