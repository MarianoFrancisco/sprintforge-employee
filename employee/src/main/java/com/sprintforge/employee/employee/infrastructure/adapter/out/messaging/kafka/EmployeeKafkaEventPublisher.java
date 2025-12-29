package com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka;

import com.sprintforge.employee.common.infrastructure.config.kafka.KafkaTopicsProperties;
import com.sprintforge.employee.employee.application.port.out.event.*;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeReactivatedKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeRetiredKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeSuspendedKafkaMessage;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.mapper.EmployeeKafkaEventMapper;
import com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka.event.EmployeeCreatedKafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@NullMarked
@Component
@RequiredArgsConstructor
public class EmployeeKafkaEventPublisher implements
        EmployeeEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topics;

    @Override
    public void publishEmployeeCreated(
            EmployeeCreatedIntegrationEvent event
    ) {
        EmployeeCreatedKafkaMessage message = EmployeeKafkaEventMapper.toMessage(event);
        String topic = topics.getEmployeeCreated();
        String key = message.employeeId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published EmployeeCreated event. topic={}, key={}", topic, key);
    }

    @Override
    public void publishEmployeeRetired(EmployeeRetiredIntegrationEvent event) {
        EmployeeRetiredKafkaMessage message = EmployeeKafkaEventMapper.toMessage(event);
        String topic = topics.getEmployeeRetired();
        String key = message.employeeId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published EmployeeRetired event. topic={}, key={}", topic, key);
    }

    @Override
    public void publishEmployeeSuspended(EmployeeSuspendedIntegrationEvent event) {
        EmployeeSuspendedKafkaMessage message = EmployeeKafkaEventMapper.toMessage(event);
        String topic = topics.getEmployeeSuspended();
        String key = message.employeeId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published EmployeeSuspended event. topic={}, key={}", topic, key);

    }

    @Override
    public void publishEmployeeReactivated(EmployeeReactivatedIntegrationEvent event) {
        EmployeeReactivatedKafkaMessage message = EmployeeKafkaEventMapper.toMessage(event);
        String topic = topics.getEmployeeReactivated();
        String key = message.employeeId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published EmployeeReactivated event. topic={}, key={}", topic, key);
    }
}
