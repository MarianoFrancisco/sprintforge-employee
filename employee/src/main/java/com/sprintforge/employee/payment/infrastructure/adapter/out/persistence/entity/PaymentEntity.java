package com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity.EmployeeEntity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class PaymentEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    private LocalDate date;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private BigDecimal deduction;
    private BigDecimal total;
    private String notes;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
