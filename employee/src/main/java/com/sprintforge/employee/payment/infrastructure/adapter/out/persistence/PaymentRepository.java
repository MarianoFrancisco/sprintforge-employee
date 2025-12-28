package com.sprintforge.employee.payment.infrastructure.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.sprintforge.employee.employee.domain.Employee;
import com.sprintforge.employee.employee.infrastructure.adapter.out.persistence.mapper.EmployeeEntityMapper;
import com.sprintforge.employee.payment.application.port.in.GetAllPaymentsQuery;
import com.sprintforge.employee.payment.application.port.out.persistence.FindPayments;
import com.sprintforge.employee.payment.application.port.out.persistence.SavePayment;
import com.sprintforge.employee.payment.domain.Payment;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.mapper.PaymentEntityMapper;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.repository.PaymentJpaRepository;
import com.sprintforge.employee.payment.infrastructure.adapter.out.persistence.specification.PaymentSpecs;

import lombok.RequiredArgsConstructor;

@NullMarked
@Repository
@RequiredArgsConstructor
public class PaymentRepository implements FindPayments, SavePayment {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = PaymentEntityMapper.toEntity(payment);
        PaymentEntity savedEntity = paymentJpaRepository.save(entity);
        return PaymentEntityMapper.toDomain(savedEntity);
    }

    @Override
    public List<Payment> findAll(GetAllPaymentsQuery query) {
        Specification<PaymentEntity> spec = PaymentSpecs.withFilters(
                query.employee(),
                query.position(),
                query.fromDate(),
                query.toDate()
        );
        List<PaymentEntity> entities = paymentJpaRepository.findAll(spec);
        return entities.stream()
                .map(PaymentEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Payment> findLastPaymentByEmployee(Employee employee) {
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "date"));

        return paymentJpaRepository
                .findAll(PaymentSpecs.employeeCuiEquals(employee.getCui().value()), pageable)
                .stream()
                .findFirst()
                .map(PaymentEntityMapper::toDomain);
    }

    @Override
    public boolean existsByEmployeeAndDate(Employee employee, LocalDate date) {
        return paymentJpaRepository.existsByEmployeeAndDate(
                EmployeeEntityMapper.toEntity(employee),
                date
        );
    }
}
