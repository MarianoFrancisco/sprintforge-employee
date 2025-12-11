package com.sprintforge.employee.common.infrastructure.config.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.topics")
public record KafkaTopicsProperties(
        String employeeCreated
) {
}
