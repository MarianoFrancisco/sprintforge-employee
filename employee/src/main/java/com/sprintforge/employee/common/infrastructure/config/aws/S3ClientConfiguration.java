package com.sprintforge.employee.common.infrastructure.config.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.s3.S3AsyncClient;

@Configuration
public class S3ClientConfiguration {

    @Bean
    public S3AsyncClient s3AsyncClient() {
        return S3AsyncClient.builder()
                .build();
    }
}
