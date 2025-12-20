package com.sprintforge.employee.employee.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

public class EmployeeImageUploadException extends BusinessException {
    public EmployeeImageUploadException(String message) {
        super(message);
    }
}
