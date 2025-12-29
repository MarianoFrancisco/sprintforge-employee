package com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.mapper;

import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeReactivatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeRetiredIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeSuspendedIntegrationEvent;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeCreatedKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeReactivatedKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeRetiredKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeSuspendedKafkaMessage;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeKafkaEventMapperTest {

    private static final UUID EMPLOYEE_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    @Test
    void shouldMapEmployeeCreatedEventToMessage() {
        EmployeeCreatedIntegrationEvent event = new EmployeeCreatedIntegrationEvent(
                EMPLOYEE_ID,
                "1234567890101",
                "john@test.com",
                "John",
                "Doe",
                "John Doe",
                "ACTIVE"
        );

        EmployeeCreatedKafkaMessage msg = EmployeeKafkaEventMapper.toMessage(event);

        assertAll(
                () -> assertEquals(event.employeeId(), msg.employeeId()),
                () -> assertEquals(event.cui(), msg.cui()),
                () -> assertEquals(event.email(), msg.email()),
                () -> assertEquals(event.firstName(), msg.firstName()),
                () -> assertEquals(event.lastName(), msg.lastName()),
                () -> assertEquals(event.fullName(), msg.fullName()),
                () -> assertEquals(event.status(), msg.status())
        );
    }

    @Test
    void shouldMapEmployeeRetiredEventToMessage() {
        EmployeeRetiredIntegrationEvent event = new EmployeeRetiredIntegrationEvent(
                EMPLOYEE_ID,
                "1234567890101",
                "john@test.com",
                "John Doe",
                "TERMINATED"
        );

        EmployeeRetiredKafkaMessage msg = EmployeeKafkaEventMapper.toMessage(event);

        assertAll(
                () -> assertEquals(event.employeeId(), msg.employeeId()),
                () -> assertEquals(event.cui(), msg.cui()),
                () -> assertEquals(event.email(), msg.email()),
                () -> assertEquals(event.fullName(), msg.fullName()),
                () -> assertEquals(event.status(), msg.status())
        );
    }

    @Test
    void shouldMapEmployeeSuspendedEventToMessage() {
        EmployeeSuspendedIntegrationEvent event = new EmployeeSuspendedIntegrationEvent(
                EMPLOYEE_ID,
                "1234567890101",
                "john@test.com",
                "John Doe",
                "SUSPENDED"
        );

        EmployeeSuspendedKafkaMessage msg = EmployeeKafkaEventMapper.toMessage(event);

        assertAll(
                () -> assertEquals(event.employeeId(), msg.employeeId()),
                () -> assertEquals(event.cui(), msg.cui()),
                () -> assertEquals(event.email(), msg.email()),
                () -> assertEquals(event.fullName(), msg.fullName()),
                () -> assertEquals(event.status(), msg.status())
        );
    }

    @Test
    void shouldMapEmployeeReactivatedEventToMessage() {
        EmployeeReactivatedIntegrationEvent event = new EmployeeReactivatedIntegrationEvent(
                EMPLOYEE_ID,
                "1234567890101",
                "john@test.com",
                "John Doe",
                "ACTIVE"
        );

        EmployeeReactivatedKafkaMessage msg = EmployeeKafkaEventMapper.toMessage(event);

        assertAll(
                () -> assertEquals(event.employeeId(), msg.employeeId()),
                () -> assertEquals(event.cui(), msg.cui()),
                () -> assertEquals(event.email(), msg.email()),
                () -> assertEquals(event.fullName(), msg.fullName()),
                () -> assertEquals(event.status(), msg.status())
        );
    }
}
