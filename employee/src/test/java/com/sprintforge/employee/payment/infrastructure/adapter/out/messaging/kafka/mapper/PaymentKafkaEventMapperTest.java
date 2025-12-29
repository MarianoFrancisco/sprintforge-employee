package com.sprintforge.employee.payment.infrastructure.adapter.out.messaging.kafka.mapper;

import com.sprintforge.employee.payment.application.port.out.event.PaidEmployeeIntegrationEvent;
import com.sprintforge.employee.payment.infrastructure.adapter.out.messaging.kafka.event.PaidEmployeeKafkaMessage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentKafkaEventMapperTest {

    @Test
    void shouldMapToKafkaMessage() {
        UUID paymentId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        LocalDate date = LocalDate.of(2025, 1, 15);

        PaidEmployeeIntegrationEvent event = new PaidEmployeeIntegrationEvent(
                paymentId, employeeId, date,
                new BigDecimal("5000.00"),
                new BigDecimal("100.00"),
                new BigDecimal("50.00"),
                new BigDecimal("5050.00")
        );

        PaidEmployeeKafkaMessage msg = PaymentKafkaEventMapper.toMessage(event);

        assertAll(
                () -> assertEquals(paymentId, msg.paymentId()),
                () -> assertEquals(employeeId, msg.employeeId()),
                () -> assertEquals(date, msg.date()),
                () -> assertEquals(new BigDecimal("5000.00"), msg.baseSalary()),
                () -> assertEquals(new BigDecimal("100.00"), msg.bonus()),
                () -> assertEquals(new BigDecimal("50.00"), msg.deduction()),
                () -> assertEquals(new BigDecimal("5050.00"), msg.total())
        );
    }
}
