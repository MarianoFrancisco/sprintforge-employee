package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.sprintforge.employee.employee.application.port.in.command.ValidateEmployees;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeesByIds;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.GetEmployeesByIdsRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.ValidateEmployeesRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper.EmployeeRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal-api/v1/employee")
public class InternalEmployeeController {

    private final GetEmployeesByIds getEmployeesByIdIn;
    private final ValidateEmployees validateEmployees;

    @PostMapping("/get-by-ids")
    public List<EmployeeResponseDTO> getByIds(
            @RequestBody @Valid GetEmployeesByIdsRequestDTO dto
    ) {
        return getEmployeesByIdIn.handle(
                        EmployeeRestMapper.toQueryByIds(dto)
                ).stream()
                .map(EmployeeRestMapper::toResponse)
                .toList();
    }

    @PostMapping("/validate-ids")
    @ResponseStatus(NO_CONTENT)
    public void validateEmployees(
            @RequestBody @Valid ValidateEmployeesRequestDTO dto
    ) {
        validateEmployees.handle(
                EmployeeRestMapper.toValidateCommnd(dto)
        );
    }
}
