package com.sprintforge.employee.payment.infrastructure.adapter.out.messaging.kafka;

import com.sprintforge.employee.common.infrastructure.config.kafka.KafkaTopicsProperties;
import com.sprintforge.employee.payment.application.port.out.event.PaidEmployeeIntegrationEvent;
import com.sprintforge.employee.payment.infrastructure.adapter.out.messaging.kafka.event.PaidEmployeeKafkaMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentKafkaEventPublisherTest {

    private static final String TOPIC = "paid-employee-topic";

    private static final UUID PAYMENT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID EMPLOYEE_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final LocalDate DATE = LocalDate.of(2025, 1, 15);

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private KafkaTopicsProperties topics;

    @InjectMocks
    private PaymentKafkaEventPublisher publisher;

    @Test
    void shouldPublishPaidEmployeeEvent() {
        when(topics.getPaidEmployee()).thenReturn(TOPIC);

        PaidEmployeeIntegrationEvent event = new PaidEmployeeIntegrationEvent(
                PAYMENT_ID,
                EMPLOYEE_ID,
                DATE,
                new BigDecimal("5000.00"),
                new BigDecimal("100.00"),
                new BigDecimal("50.00"),
                new BigDecimal("5050.00")
        );

        publisher.publishPaidEmployee(event);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), valueCaptor.capture());
        verify(topics).getPaidEmployee();
        verifyNoMoreInteractions(kafkaTemplate, topics);

        assertAll(
                () -> assertEquals(TOPIC, topicCaptor.getValue()),
                () -> assertEquals(PAYMENT_ID.toString(), keyCaptor.getValue()),
                () -> assertTrue(valueCaptor.getValue() instanceof PaidEmployeeKafkaMessage)
        );

        PaidEmployeeKafkaMessage msg = (PaidEmployeeKafkaMessage) valueCaptor.getValue();

        assertAll(
                () -> assertEquals(PAYMENT_ID, msg.paymentId()),
                () -> assertEquals(EMPLOYEE_ID, msg.employeeId()),
                () -> assertEquals(DATE, msg.date()),
                () -> assertEquals(new BigDecimal("5000.00"), msg.baseSalary()),
                () -> assertEquals(new BigDecimal("100.00"), msg.bonus()),
                () -> assertEquals(new BigDecimal("50.00"), msg.deduction()),
                () -> assertEquals(new BigDecimal("5050.00"), msg.total())
        );
    }
}
