package com.sprintforge.employee.employee.application.port.out.storage;

import java.util.Optional;

import com.sprintforge.employee.employee.application.port.in.command.ImageContent;
import com.sprintforge.employee.employee.domain.valueobject.EmployeeProfileImageKey;

/**
 * Employee Image Storage Port
 */
public interface EmployeeImageStorage {
    /**
     * Uploads an employee image and returns the stored image key
     * @param imageContent Content of the image to upload
     * @return Employee profile image key
     */
    EmployeeProfileImageKey uploadEmployeeImage(ImageContent imageContent);

    /**
     * Retrieves the URL of the employee image by its key
     * @param key Employee profile image key
     * @return Optional containing the image URL if found, otherwise empty
     */
    Optional<String> getEmployeeImageUrl(EmployeeProfileImageKey key);
}
