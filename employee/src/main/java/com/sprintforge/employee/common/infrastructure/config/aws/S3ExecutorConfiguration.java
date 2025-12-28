package com.sprintforge.employee.common.infrastructure.config.aws;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3ExecutorConfiguration {

    @Bean(destroyMethod = "shutdown")
    public ExecutorService s3Executor() {
        return Executors.newSingleThreadExecutor();
    }
}
