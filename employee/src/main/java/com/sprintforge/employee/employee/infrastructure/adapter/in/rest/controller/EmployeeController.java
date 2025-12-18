package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.application.port.in.query.*;
import com.sprintforge.employee.employee.application.port.in.command.*;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.*;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper.EmployeeRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final GetAllEmployees getAllEmployees;
    private final GetEmployeeByCui getEmployeeByCui;
    private final GetEmployeeByEmail getEmployeeByEmail;
    private final GetEmployeeById getEmployeeById;
    private final HireEmployee hireEmployee;
    private final UpdateEmployeeDetail updateEmployeeDetail;
    private final IncreaseEmployeeSalary increaseEmployeeSalary;
    private final ReinstateEmployee reinstateEmployee;
    private final SuspendEmployee suspendEmployee;
    private final TerminateEmployee terminateEmployee;

    @GetMapping
    public List<EmployeeResponseDTO> getAll(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        List<Employee> employees = getAllEmployees.handle(
                EmployeeRestMapper.toQuery(
                        searchTerm,
                        isActive,
                        isDeleted
                )
        );
        return employees.stream()
                .map(EmployeeRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/cui/{cui}")
    public EmployeeResponseDTO getByCui(@PathVariable String cui) {
        Employee employee = getEmployeeByCui.handle(
                EmployeeRestMapper.toQueryByCui(cui)
        );
        return EmployeeRestMapper.toResponse(employee);
    }

    @GetMapping("/email/{email}")
    public EmployeeResponseDTO getByEmail(@PathVariable String email) {
        Employee employee = getEmployeeByEmail.handle(
                EmployeeRestMapper.toQueryByEmail(email)
        );
        return EmployeeRestMapper.toResponse(employee);
    }

    @GetMapping("/{id}")
    public EmployeeResponseDTO getById(@PathVariable UUID id) {
        Employee employee = getEmployeeById.handle(
                EmployeeRestMapper.toQueryById(id)
        );
        return EmployeeRestMapper.toResponse(employee);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public EmployeeResponseDTO hire(
            @ModelAttribute @Valid HireEmployeeRequestDTO dto
    ) {
        Employee saved = hireEmployee.handle(
                EmployeeRestMapper.toHireCommand(dto)
        );
        return EmployeeRestMapper.toResponse(saved);
    }

    @PatchMapping("/{id}")
    public EmployeeResponseDTO updateDetails(
            @PathVariable UUID id,
            @ModelAttribute UpdateEmployeeDetailRequestDTO dto
    ) {
        Employee updated = updateEmployeeDetail.handle(
                EmployeeRestMapper.toUpdateCommand(
                        id,
                        dto
                )
        );
        return EmployeeRestMapper.toResponse(updated);
    }

    @PostMapping("/{cui}/salary/increase")
    public EmployeeResponseDTO increaseSalary(
        @PathVariable String cui,
        @Validated @RequestBody IncreaseEmployeeSalaryRequestDTO dto
    ) {
        Employee updated = increaseEmployeeSalary.handle(
                EmployeeRestMapper.toIncreaseSalaryCommand(
                        cui,
                        dto
                )
        );
        return EmployeeRestMapper.toResponse(updated);
    }

    @PostMapping("/{cui}/reinstate")
    public EmployeeResponseDTO reinstate(
        @PathVariable String cui,
        @Validated @RequestBody ReinstateEmployeeRequestDTO dto
    ) {
        Employee updated = reinstateEmployee.handle(
                EmployeeRestMapper.toReinstateCommand(
                        cui,
                        dto
                )
        );
        return EmployeeRestMapper.toResponse(updated);
    }

    @PostMapping("/{cui}/suspend")
    public EmployeeResponseDTO suspend(
        @PathVariable String cui,
        @Validated @RequestBody SuspendEmployeeRequestDTO dto
    ) {
        Employee updated = suspendEmployee.handle(
                EmployeeRestMapper.toSuspendCommand(
                        cui,
                        dto
                )
        );
        return EmployeeRestMapper.toResponse(updated);
    }

    @PostMapping("/{cui}/terminate")
    public EmployeeResponseDTO terminate(
        @PathVariable String cui,
        @Validated @RequestBody TerminateEmployeeRequestDTO dto
    ) {
        Employee updated = terminateEmployee.handle(
                EmployeeRestMapper.toTerminateCommand(
                        cui,
                        dto
                )
        );
        return EmployeeRestMapper.toResponse(updated);
    }
}
