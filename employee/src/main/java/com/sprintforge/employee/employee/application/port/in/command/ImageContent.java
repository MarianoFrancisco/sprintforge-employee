package com.sprintforge.employee.employee.application.port.in.command;

import java.io.InputStream;

import com.sprintforge.common.domain.exception.ValidationException;

/**
 * Validated image content container.
 * 
 * @param data        Image data stream (non-null)
 * @param size        Image size in bytes (0 < size ≤ 9 MB)
 * @param contentType MIME type starting with "image/" (non-null)
 * @throws ValidationException if validation fails
 */
public record ImageContent(
        InputStream data,
        long size,
        String contentType) {
    /** Maximum allowed image size: 9 MB */
    private static final long MAX_IMAGE_SIZE = 9 * 1024 * 1024;

    /**
     * Validates image data, size, and content type.
     * 
     * @throws ValidationException for invalid parameters
     */
    public ImageContent {
        if (data == null) {
            throw new ValidationException("Datos de imagen no pueden ser nulos");
        }

        if (contentType == null) {
            throw new ValidationException("Tipo de contenido de imagen no puede ser nulo");
        }

        if (size <= 0) {
            throw new ValidationException("El tamaño de la imagen debe ser mayor que cero");
        }

        if (size > MAX_IMAGE_SIZE) {
            throw new ValidationException(
                    "El tamaño de la imagen no puede exceder " + MAX_IMAGE_SIZE / 1024 / 1024 + " MB");
        }

        if (!contentType.startsWith("image/")) {
            throw new ValidationException("Tipo de contenido de imagen inválido: " + contentType);
        }
    }
}
