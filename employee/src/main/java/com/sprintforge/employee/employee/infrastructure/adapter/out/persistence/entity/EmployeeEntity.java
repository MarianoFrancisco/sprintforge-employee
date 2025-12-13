package com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.entity;

import com.sprintforge.employee.employee.domain.valueobject.EmployeeWorkloadType;
import com.sprintforge.employee.position.infrastructure.adapter.out.persistence.entity.PositionEntity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    private UUID id;

    private String cui;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private PositionEntity position;

    @Enumerated(EnumType.STRING)
    @Column(name = "workload_type", nullable = false, length = 20)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private EmployeeWorkloadType workloadType;

    private BigDecimal salary;
    private BigDecimal igssPercentage;
    private BigDecimal irtraPercentage;
    private String profileImage;
    private boolean isActive;
    private boolean isDeleted;
    private Instant createdAt;
    private Instant updatedAt;
}
