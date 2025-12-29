package com.sprintforge.employee.payment.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentNotesTest {

    @Test
    void shouldAllowNullNotes() {
        PaymentNotes notes = new PaymentNotes(null);
        assertNull(notes.value());
    }

    @Test
    void shouldTrimNotes() {
        PaymentNotes notes = new PaymentNotes("   hola   ");
        assertEquals("hola", notes.value());
    }

    @Test
    void shouldThrowWhenNotesTooLong() {
        String tooLong = "a".repeat(256);
        assertThrows(ValidationException.class, () -> new PaymentNotes(tooLong));
    }
}
