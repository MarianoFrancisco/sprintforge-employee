package com.sprintforge.employee.employee.infrastructure.adapter.out.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageProperties {
    private final S3 s3 = new S3();
    private final Cdn cdn = new Cdn();

    @Getter
    @Setter
    public static class S3 {
        private String bucket;
        private String region;
        private String avatarPrefix;
    }

    @Getter
    @Setter
    public static class Cdn {
        private String cloudfrontBaseUrl;
    }
}
