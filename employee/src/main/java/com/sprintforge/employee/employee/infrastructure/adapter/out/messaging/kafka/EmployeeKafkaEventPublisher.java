package com.sprintforge.employee.employee.infrastructure.adapter.out.messaging.kafka;

import com.sprintforge.employee.common.infrastructure.config.kafka.KafkaTopicsProperties;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeCreatedIntegrationEvent;
import com.sprintforge.employee.employee.application.port.out.event.EmployeeEventPublisher;
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
public class EmployeeKafkaEventPublisher implements EmployeeEventPublisher {

    private final KafkaTemplate<String, EmployeeCreatedKafkaMessage> kafkaTemplate;
    private final KafkaTopicsProperties topics;

    @Override
    public void publishEmployeeCreated(
            EmployeeCreatedIntegrationEvent event
    ) {
        EmployeeCreatedKafkaMessage message = EmployeeKafkaEventMapper.toMessage(event);
        String topic = topics.employeeCreated();
        String key = message.employeeId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published EmployeeCreated event. topic={}, key={}", topic, key);
    }
}
