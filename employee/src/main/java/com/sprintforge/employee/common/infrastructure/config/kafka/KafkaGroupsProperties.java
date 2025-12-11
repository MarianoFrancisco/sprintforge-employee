package com.sprintforge.employee.common.infrastructure.config.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.groups")
public record KafkaGroupsProperties(

) {
}
