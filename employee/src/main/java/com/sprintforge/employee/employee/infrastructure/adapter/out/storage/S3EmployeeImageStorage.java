package com.sprintforge.employee.employee.infrastructure.adapter.out.storage;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.springframework.stereotype.Component;

import com.sprintforge.employee.employee.application.exception.EmployeeImageUploadException;
import com.sprintforge.employee.employee.application.port.in.command.ImageContent;
import com.sprintforge.employee.employee.application.port.out.storage.EmployeeImageStorage;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeProfileImageKey;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Component
@RequiredArgsConstructor
public class S3EmployeeImageStorage implements EmployeeImageStorage {

    private final S3AsyncClient s3AsyncClient;
    private final ExecutorService s3Executor;
    private final StorageProperties storageProperties;

    @Override
    public EmployeeProfileImageKey uploadEmployeeImage(ImageContent imageContent) {

        String objectKey = buildProfileImageKey(UUID.randomUUID().toString());

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(storageProperties.getS3().getBucket())
                .key(objectKey)
                .contentType(imageContent.contentType())
                .contentLength(imageContent.size())
                .build();

        CompletableFuture<PutObjectResponse> future = s3AsyncClient.putObject(
                request,
                AsyncRequestBody.fromInputStream(
                        imageContent.data(),
                        imageContent.size(),
                        s3Executor));

        try {
            future.join();
            return new EmployeeProfileImageKey(objectKey);

        } catch (Exception ex) {
            throw new EmployeeImageUploadException("Ha ocurrido un error al subir la imagen del empleado .");
        }
    }

    @Override
    public Optional<String> getEmployeeImageUrl(EmployeeProfileImageKey key) {
        if (key.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(String.format("%s/%s",
                storageProperties.getCdn().getCloudfrontBaseUrl(),
                key.value()));

    }

    private String buildProfileImageKey(String key) {
        return String.format("%s/%s",
                storageProperties.getS3().getAvatarPrefix(),
                key);
    }
}
