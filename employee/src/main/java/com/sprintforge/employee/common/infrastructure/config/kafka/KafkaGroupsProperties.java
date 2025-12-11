package com.sprintforge.employee.common.infrastructure.config.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.kafka.groups")
public class KafkaGroupsProperties {
    private String defaultGroup;
}
