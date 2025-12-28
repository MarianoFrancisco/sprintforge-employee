package com.sprintforge.employee.payment.infrastructure.adapter.out.messaging.kafka;

import com.sprintforge.employee.common.infrastructure.config.kafka.KafkaTopicsProperties;
import com.sprintforge.employee.payment.application.port.out.event.PaidEmployeeIntegrationEvent;
import com.sprintforge.employee.payment.application.port.out.event.PaymentEventPublisher;
import com.sprintforge.employee.payment.infrastructure.adapter.out.messaging.kafka.event.PaidEmployeeKafkaMessage;
import com.sprintforge.employee.payment.infrastructure.adapter.out.messaging.kafka.mapper.PaymentKafkaEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@NullMarked
@Component
@RequiredArgsConstructor
public class PaymentKafkaEventPublisher implements PaymentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topics;

    @Override
    public void publishPaidEmployee(PaidEmployeeIntegrationEvent event) {

        PaidEmployeeKafkaMessage message = PaymentKafkaEventMapper.toMessage(event);
        String topic = topics.getPaidEmployee();
        String key = message.paymentId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published PaidEmployee event. topic={}, key={}", topic, key);
    }
}
