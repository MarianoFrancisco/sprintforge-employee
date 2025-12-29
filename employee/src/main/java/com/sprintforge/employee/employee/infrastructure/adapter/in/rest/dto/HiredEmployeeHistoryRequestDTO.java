package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.dto;

import com.sprintforge.common.infrastructure.validation.END_AFTER_START;

import java.time.LocalDate;

@END_AFTER_START(
        start = "from",
        end = "to",
        allowEqual = true,
        message = "La fecha de fin no puede ser anterior a la fecha de inicio"
)
public record HiredEmployeeHistoryRequestDTO(
        LocalDate from,

        LocalDate to
) {
}
