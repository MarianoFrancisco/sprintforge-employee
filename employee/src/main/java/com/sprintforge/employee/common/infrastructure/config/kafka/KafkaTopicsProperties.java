package com.sprintforge.employee.common.infrastructure.config.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.kafka.topics")
public class KafkaTopicsProperties {
    String employeeCreated;
    String employeeRetired;
    String employeeSuspended;
    String employeeReactivated;
    String paidEmployee;
}
