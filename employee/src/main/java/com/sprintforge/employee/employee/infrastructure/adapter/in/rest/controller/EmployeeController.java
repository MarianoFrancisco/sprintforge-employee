package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.sprintforge.employee.employee.application.port.in.command.ActivateEmployee;
import com.sprintforge.employee.employee.application.port.in.command.DeactivateEmployee;
import com.sprintforge.employee.employee.application.port.in.command.DeleteEmployee;
import com.sprintforge.employee.employee.application.port.in.query.GetAllEmployees;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByCui;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeByEmail;
import com.sprintforge.employee.employee.application.port.in.query.GetEmployeeById;
import com.sprintforge.employee.employee.application.port.in.command.HireEmployee;
import com.sprintforge.employee.employee.application.port.in.command.UpdateEmployeeDetail;
import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto.HireEmployeeRequestDTO;
import com.sprintforge.employee.employee.infrastructure.adapter.in.rest.mapper.EmployeeRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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
    private final ActivateEmployee activateEmployee;
    private final DeactivateEmployee deactivateEmployee;
    private final DeleteEmployee deleteEmployee;

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
            @ModelAttribute HireEmployeeRequestDTO dto
    ) {
        Employee saved = hireEmployee.handle(
                EmployeeRestMapper.toHireCommand(dto)
        );
        return EmployeeRestMapper.toResponse(saved);
    }

    @PatchMapping("/{id}")
    public EmployeeResponseDTO updateDetails(
            @PathVariable UUID id,
            @ModelAttribute HireEmployeeRequestDTO dto
    ) {
        Employee updated = updateEmployeeDetail.handle(
                EmployeeRestMapper.toUpdateCommand(
                        id,
                        dto
                )
        );
        return EmployeeRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}:activate")
    public EmployeeResponseDTO activate(@PathVariable UUID id) {
        Employee activated = activateEmployee.handle(
                EmployeeRestMapper.toActivateCommand(id)
        );
        return EmployeeRestMapper.toResponse(activated);
    }

    @PatchMapping("/{id}:deactivate")
    public EmployeeResponseDTO deactivate(@PathVariable UUID id) {
        Employee deactivated = deactivateEmployee.handle(
                EmployeeRestMapper.toDeactivateCommand(id)
        );
        return EmployeeRestMapper.toResponse(deactivated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        deleteEmployee.handle(
                EmployeeRestMapper.toDeleteCommand(id)
        );
    }
}
