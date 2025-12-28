package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintforge.employee.employee.application.port.in.query.*;
import com.sprintforge.employee.employee.domain.EmploymentHistory;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmploymentHistoryResponseDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper.EmploymentHistoryRestMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee/history")
public class EmploymentHistoryController {
    private final GetAllEmploymentHistories getAllEmploymentHistories;

    @GetMapping
    public List<EmploymentHistoryResponseDTO> getAll(@Valid GetAllEmploymentHistoriesQuery query) {
        List<EmploymentHistory> employmentHistories = getAllEmploymentHistories.handle(query);
        List<EmploymentHistoryResponseDTO> response = employmentHistories.stream()
                .map(EmploymentHistoryRestMapper::toResponse)
                .toList();
        return response;
    }
    
}
