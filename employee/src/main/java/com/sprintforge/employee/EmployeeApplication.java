package com.sprintforge.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.sprintforge")
@ConfigurationPropertiesScan(basePackages = "com.sprintforge")
public class EmployeeApplication {

    static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

}
