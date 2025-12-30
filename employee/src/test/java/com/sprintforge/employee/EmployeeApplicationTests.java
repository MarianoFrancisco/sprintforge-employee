package com.sprintforge.employee;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeApplicationTests {

    @BeforeAll
    static void setUpAwsRegion() {
        System.setProperty("aws.region", "us-east-2");
        System.setProperty("AWS_REGION", "us-east-2");
    }

    @Test
    void contextLoads() {
    }
}
