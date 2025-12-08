package com.sprintforge.employee.employee.application.port.out.persistence;

public interface ExistsEmployeeByEmail {
    boolean existsByEmail(String email);
}
