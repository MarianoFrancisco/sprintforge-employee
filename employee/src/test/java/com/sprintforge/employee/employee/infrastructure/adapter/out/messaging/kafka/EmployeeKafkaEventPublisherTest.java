package com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka;

import com.sprintforge.employee.common.infrastructure.config.kafka.KafkaTopicsProperties;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeReactivatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeRetiredIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeSuspendedIntegrationEvent;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeCreatedKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeReactivatedKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeRetiredKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeSuspendedKafkaMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeKafkaEventPublisherTest {

    private static final UUID EMPLOYEE_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private KafkaTopicsProperties topics;

    @InjectMocks
    private EmployeeKafkaEventPublisher publisher;

    @Test
    void shouldPublishEmployeeCreated() {
        when(topics.getEmployeeCreated()).thenReturn("employee.created");

        EmployeeCreatedIntegrationEvent event = new EmployeeCreatedIntegrationEvent(
                EMPLOYEE_ID, "1234567890101", "john@test.com",
                "John", "Doe", "John Doe", "ACTIVE"
        );

        publisher.publishEmployeeCreated(event);

        ArgumentCaptor<Object> messageCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(eq("employee.created"), eq(EMPLOYEE_ID.toString()), messageCaptor.capture());
        assert messageCaptor.getValue() instanceof EmployeeCreatedKafkaMessage;
    }

    @Test
    void shouldPublishEmployeeRetired() {
        when(topics.getEmployeeRetired()).thenReturn("employee.retired");

        EmployeeRetiredIntegrationEvent event = new EmployeeRetiredIntegrationEvent(
                EMPLOYEE_ID, "1234567890101", "john@test.com", "John Doe", "TERMINATED"
        );

        publisher.publishEmployeeRetired(event);

        ArgumentCaptor<Object> messageCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(eq("employee.retired"), eq(EMPLOYEE_ID.toString()), messageCaptor.capture());
        assert messageCaptor.getValue() instanceof EmployeeRetiredKafkaMessage;
    }

    @Test
    void shouldPublishEmployeeSuspended() {
        when(topics.getEmployeeSuspended()).thenReturn("employee.suspended");

        EmployeeSuspendedIntegrationEvent event = new EmployeeSuspendedIntegrationEvent(
                EMPLOYEE_ID, "1234567890101", "john@test.com", "John Doe", "SUSPENDED"
        );

        publisher.publishEmployeeSuspended(event);

        ArgumentCaptor<Object> messageCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(eq("employee.suspended"), eq(EMPLOYEE_ID.toString()), messageCaptor.capture());
        assert messageCaptor.getValue() instanceof EmployeeSuspendedKafkaMessage;
    }

    @Test
    void shouldPublishEmployeeReactivated() {
        when(topics.getEmployeeReactivated()).thenReturn("employee.reactivated");

        EmployeeReactivatedIntegrationEvent event = new EmployeeReactivatedIntegrationEvent(
                EMPLOYEE_ID, "1234567890101", "john@test.com", "John Doe", "ACTIVE"
        );

        publisher.publishEmployeeReactivated(event);

        ArgumentCaptor<Object> messageCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(eq("employee.reactivated"), eq(EMPLOYEE_ID.toString()), messageCaptor.capture());
        assert messageCaptor.getValue() instanceof EmployeeReactivatedKafkaMessage;
    }
}